package plugins;

import com.google.common.base.Function;
import models.Applicant;
import models.Document;
import models.Member;
import play.Application;
import play.Plugin;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.FluentIterable.from;

public class ApplicantProducerPlugin extends Plugin {

    private final Application application;

    public ApplicantProducerPlugin(Application application) {
        this.application = application;
    }

    public static void createApplicant(Http.MultipartFormData data) {
        Map<String, String[]> values = data.asFormUrlEncoded();
        String subject = values.get("headers[Subject]")[0];
        String to = values.get("envelope[to]")[0];
        Member member = Member.find.byId(to.split("\\+")[1].split("@")[0]);
        Applicant applicant = addApplicantInCompany(subject, member);
        addDocuments(data.getFiles(), applicant);
    }

    private static Applicant addApplicantInCompany(String firstname, Member member) {
        final Applicant applicant = new Applicant(firstname, member.getAccount());
        member.getAccount().addApplicant(applicant);
        member.getAccount().update();
        return applicant;
    }

    private static void addDocuments(List<Http.MultipartFormData.FilePart> files, final Applicant applicant) {
        from(files).transform(new Function<Http.MultipartFormData.FilePart, Document>() {
            @Nullable
            @Override
            public Document apply(@Nullable Http.MultipartFormData.FilePart file) {
                Document document = StoragePlugin.store(file.getFile(), applicant.getFilePath(file), file.getContentType());
                applicant.addDocument(document);
                return document;
            }
        });
        applicant.update();
    }

}
