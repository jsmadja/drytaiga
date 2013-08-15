package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.Http;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Entity
public class Applicant extends Model implements Documentable {

    @Id
    private Long id;

    @Constraints.Required
    private String firstname;

    @Constraints.Required
    private String lastname;

    @Constraints.Required
    @Formats.NonEmpty
    private String email;

    @ManyToMany
    private List<Position> positions = new ArrayList<Position>();

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<Document>();

    @OneToOne
    private Account account;

    @ManyToOne
    private Company company;

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
    public Long getId() {
        return id;
    }

    @Override
    public List<Document> getDocuments() {
        return documents;
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    @Override
    public String getFilePath(Http.MultipartFormData.FilePart file) {
        return format("companies/%d/applicants/%d/%s", company.getId(), id, file.getFilename());
    }
}
