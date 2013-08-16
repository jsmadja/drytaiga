package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.Http;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.defaultString;

@Entity
public class Applicant extends BaseModel implements Documentable {

    @Constraints.Required
    private String firstname;

    @Constraints.Required
    private String lastname;

    @Constraints.Required
    @Formats.NonEmpty
    private String email;

    @ManyToMany
    private List<Opening> openings = new ArrayList<Opening>();

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<Document>();

    @ManyToOne
    private Company company;

    public static Model.Finder<Long, Applicant> find = new Model.Finder(Long.class, Applicant.class);

    public Applicant(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Applicant(String firstname, Account account) {
        this.firstname = firstname;
        this.account = account;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    @Override
    public List<Document> getDocuments() {
        return documents;
    }

    public void addOpening(Opening opening) {
        openings.add(opening);
    }

    @Override
    public String getFilePath(Http.MultipartFormData.FilePart file) {
        return format("companies/%d/applicants/%d/%s", company.getId(), getId(), file.getFilename());
    }

    public String getFullname() {
        return new String(defaultString(firstname) + " " + defaultString(lastname)).trim();
    }

    @Override
    public Class getType() {
        return Applicant.class;
    }
}
