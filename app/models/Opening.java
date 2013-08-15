package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.Http;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Entity
public class Opening extends Model implements Documentable {

    @Id
    private Long id;

    @Constraints.Required
    private String name;

    @ManyToMany(mappedBy = "openings", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<Applicant> applicants = new ArrayList<Applicant>();

    @OneToMany(mappedBy = "opening", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<Document>();

    @ManyToOne
    private Company company;

    @OneToOne
    private Account account;

    public static Model.Finder<Long, Opening> find = new Model.Finder(Long.class, Opening.class);

    public Opening(String name) {
        this.name = name;
    }

    public void addCandidate(Applicant applicant) {
        this.applicants.add(applicant);
        applicant.addOpening(this);
    }

    @Override
    public void addDocument(Document document) {
        document.attachTo(this);
        this.documents.add(document);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public List<Document> getDocuments() {
        return documents;
    }

    public boolean hasName(String openingName) {
        return name.equals(openingName);
    }

    public Account getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public Company getCompany() {
        return company;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFilePath(Http.MultipartFormData.FilePart file) {
        return format("companies/%d/openings/%d/%s", company.getId(), id, file.getFilename());
    }

    @Override
    public Class getType() {
        return Opening.class;
    }
}

