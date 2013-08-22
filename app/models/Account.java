package models;

import com.avaje.ebean.Expression;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.avaje.ebean.Expr.eq;

@Entity
@Table(name = "account")
public class Account extends BaseModel<Account> {

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

    public long getUsedSpace() {
        long usedSpace = 0;
        List<Document> list = Document.find.where(thisAccount()).findList();
        for (Document document : list) {
            usedSpace += document.getSize();
        }
        return usedSpace;
    }

    public Long getTotalSpace() {
        return accountType.getTotalSpace();
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

    public Integer getApplicantCount() {
        return Applicant.find.where(thisAccount()).findRowCount();
    }

    public Integer getOpeningCount() {
        return Opening.find.where(thisAccount()).findRowCount();
    }

    public int getDocumentCount() {
        return Document.find.where(thisAccount()).findRowCount();
    }

    public Integer getMemberCount() {
        return Member.find.where(thisAccount()).findRowCount();
    }

    private Expression thisAccount() {
        return eq("account", this);
    }

    public List<Member> getMembers() {
        return members;
    }

    @Override
    protected Finder getFinder() {
        return find;
    }
}
