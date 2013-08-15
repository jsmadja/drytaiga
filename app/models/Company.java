package models;

import misc.FileSize;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company extends Model {

    @Id
    private Long id;

    @Constraints.Required
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Member> members = new ArrayList<Member>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Opening> openings = new ArrayList<Opening>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Applicant> applicants = new ArrayList<Applicant>();

    @OneToOne
    private Account account;

    public static Model.Finder<Long, Company> find = new Model.Finder(Long.class, Company.class);

    public Company(String name, Account account) {
        this.name = name;
        this.account = account;
    }

    public void addMember(Member user) {
        user.setCompany(this);
        members.add(user);
    }

    public void addOpening(Opening opening) {
        openings.add(opening);
    }

    public String getUsedSpace() {
        long usedSpace = 0;
        for (Documentable documentable : getDocumentables()) {
            List<Document> documents = documentable.getDocuments();
            for (Document document : documents) {
                usedSpace += document.getSize();
            }
        }
        return new FileSize(usedSpace).toString();
    }

    public String getTotalSpace() {
        return new FileSize(account.getAccountType().getTotalSpace()).toString();
    }

    public int getNumDocuments() {
        int numDocuments = 0;
        for (Documentable documentable : getDocumentables()) {
            List<Document> documents = documentable.getDocuments();
            numDocuments += documents.size();
        }
        return numDocuments;
    }

    private List<Documentable> getDocumentables() {
        List<Documentable> documentables = new ArrayList<Documentable>();
        documentables.addAll(openings);
        documentables.addAll(applicants);
        return documentables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Company company = (Company) o;

        if (!name.equals(company.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public boolean containsOpeningWithName(String positionName) {
        for (Opening opening : openings) {
            if(opening.hasName(positionName)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Opening opening) {
        return openings.contains(opening);
    }

    public boolean contains(Applicant applicant) {
        return applicants.contains(applicant);
    }

    public List<Opening> getOpenings() {
        return openings;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void addApplicant(Applicant applicant) {
        this.applicants.add(applicant);
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }
}