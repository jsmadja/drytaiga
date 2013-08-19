package models;

import misc.FileSize;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class Account extends Model {

    @Id
    public Long id;

    private AccountType accountType;

    @OneToOne
    private Member owner;

    private AccountStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    private Company company;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Member> members = new ArrayList<Member>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Opening> openings = new ArrayList<Opening>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Applicant> applicants = new ArrayList<Applicant>();

    public static Model.Finder<String, Account> find = new Model.Finder(String.class, Account.class);

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.status = AccountStatus.Active;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
        this.owner.setAccount(this);
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Company getCompany() {
        return company;
    }

    public boolean contains(Opening opening) {
        return openings.contains(opening);
    }

    public boolean contains(Applicant applicant) {
        boolean contains = applicants.contains(applicant);
        if (contains) {
            return true;
        }
        for (Opening opening : openings) {
            if (opening.contains(applicant)) {
                return true;
            }
        }
        return false;
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
        return new FileSize(accountType.getTotalSpace()).toString();
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
            if (opening.hasName(positionName)) {
                return true;
            }
        }
        return false;
    }

    public List<Opening> getOpenings() {
        return openings;
    }

    public void addApplicant(Applicant applicant) {
        this.applicants.add(applicant);
    }

    public List<Applicant> getApplicants() {
        return this.applicants;
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
