package controllers;

import com.google.common.base.Predicate;
import misc.Resolver;
import models.ApplianceStatus;
import models.Applicant;
import models.Comment;
import models.Document;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import views.html.applicants.index;
import views.html.applicants.read;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static models.Applicant.find;
import static play.data.Form.form;

@Security.Authenticated(Secured.class)
public class Applicants extends AjaxController {

    public static final Predicate<Applicant> recentlyCreated = new Predicate<Applicant>() {
        @Override
        public boolean apply(@Nullable Applicant applicant) {
            return applicant.getCreatedAt().after(new DateTime().minus(Duration.standardMinutes(5)).toDate());
        }
    };

    public static Result list() {
        return ok(index.render(user()));
    }

    public static Result read(Long id) {
        Applicant applicant = find.byId(id);
        if (user().canAccessTo(applicant)) {
            return ok(read.render(applicant, user(), documentForm(), commentForm()));
        }
        return forbidden();
    }

    public static Result index() {
        List<Applicant> values = new ArrayList<Applicant>(filter(user().getCompany().getApplicants(), recentlyCreated));
        return toJson(values, keyResolver(), valueResolver());
    }

    public static Result changeApplianceStatus(Long id) {
        Applicant applicant = find.byId(id);
        if (user().canAccessTo(applicant)) {
            ApplianceStatus applianceStatus = ApplianceStatus.values()[Integer.valueOf(form().bindFromRequest().data().get("applianceStatus"))];
            applicant.updateStatus(applianceStatus);
            return ok();
        }
        return forbidden();
    }

    private static Form<Document> documentForm() {
        return form(Document.class);
    }

    private static Form<Comment> commentForm() {
        return form(Comment.class);
    }

    private static Resolver<Applicant> valueResolver() {
        return new Resolver<Applicant>() {
            @Override
            public String resolve(Applicant value) {
                return value.getFullname();
            }
        };
    }

    private static Resolver<Applicant> keyResolver() {
        return new Resolver<Applicant>() {
            @Override
            public String resolve(Applicant value) {
                return value.getId().toString();
            }
        };
    }

}
