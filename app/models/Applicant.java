package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.Http;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static models.ApplianceStatus.ToContact;
import static org.apache.commons.lang3.StringUtils.defaultString;

@Entity
public class Applicant extends BaseModel implements Documentable, Commentable, Taggable {

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

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<Tag>();

    private ApplianceStatus applianceStatus;

    public static Model.Finder<Long, Applicant> find = new Model.Finder(Long.class, Applicant.class);

    public Applicant(String firstname, String lastname, String email, Account account) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.account = account;
        this.applianceStatus = ToContact;
    }

    public Applicant(String firstname, Account account) {
        this.firstname = firstname;
        this.account = account;
        this.applianceStatus = ToContact;
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
        return format("companies/%d/applicants/%d/%s", getAccount().getCompany().getId(), getId(), file.getFilename());
    }

    public String getFullname() {
        return new String(defaultString(firstname) + " " + defaultString(lastname)).trim();
    }

    @Override
    public Class getType() {
        return Applicant.class;
    }


    @Override
    public void addComment(Comment comment) {
        comment.attachTo(this);
        this.comments.add(comment);
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    public void updateStatus(ApplianceStatus applianceStatus) {
        this.applianceStatus = applianceStatus;
    }

    public ApplianceStatus getApplianceStatus() {
        return applianceStatus;
    }

    @Override
    public void addTag(Tag tag) {
        tag.attachTo(this);
        this.tags.add(tag);
    }

    @Override
    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

}
