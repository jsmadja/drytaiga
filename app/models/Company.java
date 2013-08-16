package models;

import misc.FileSize;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company extends BaseModel {

    @Constraints.Required
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Member> members = new ArrayList<Member>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Opening> openings = new ArrayList<Opening>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Applicant> applicants = new ArrayList<Applicant>();

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
        boolean contains = applicants.contains(applicant);
        if(contains) {
            return true;
        }
        for (Opening opening : openings) {
            if(opening.contains(applicant)) {
                return true;
            }
        }
        return false;
    }

    public List<Opening> getOpenings() {
        return openings;
    }

    public String getName() {
        return name;
    }

    public void addApplicant(Applicant applicant) {
        this.applicants.add(applicant);
    }

    public List<Applicant> getApplicants() {
        List<Applicant> applicants = this.applicants;
        for (Opening opening : openings) {
            applicants.addAll(opening.getApplicants());
        }
        return applicants;
    }
}