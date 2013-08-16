package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.Http;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Entity
public class Opening extends BaseModel implements Documentable, Commentable {

    @Constraints.Required
    private String name;

    @ManyToMany(mappedBy = "openings", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<Applicant> applicants = new ArrayList<Applicant>();

    @OneToMany(mappedBy = "opening", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<Document>();

    @OneToMany(mappedBy = "opening", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

    public static Model.Finder<Long, Opening> find = new Model.Finder(Long.class, Opening.class);

    public Opening(String name) {
        this.name = name;
    }

    public void addApplicant(Applicant applicant) {
        this.applicants.add(applicant);
        applicant.addOpening(this);
    }

    @Override
    public void addDocument(Document document) {
        document.attachTo(this);
        this.documents.add(document);
    }

    @Override
    public List<Document> getDocuments() {
        return documents;
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

    public boolean hasName(String openingName) {
        return name.equals(openingName);
    }

    public String getName() {
        return name;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFilePath(Http.MultipartFormData.FilePart file) {
        return format("companies/%d/openings/%d/%s", getAccount().getCompany().getId(), getId(), file.getFilename());
    }

    @Override
    public Class getType() {
        return Opening.class;
    }

    public boolean contains(Applicant applicant) {
        return applicants.contains(applicant);
    }
}

