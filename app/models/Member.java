package models;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.Http;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member extends Model {

    @Id
    private Long id;

    @Constraints.Required
    @Formats.NonEmpty
    private String email;

    @Constraints.Required
    private String firstname;

    @Constraints.Required
    private String lastname;

    @Constraints.Required
    private String password;

    @ManyToOne
    private Company company;

    @OneToOne
    private Account account;

    private DateTime lastLogin;

    public static Model.Finder<String,Member> find = new Model.Finder(String.class, Member.class);

    public Member(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public static Member authenticate(String email, String password) {
        return find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }

    public static Member findByEmail(String email) {
        return find.where()
                .eq("email", email)
                .findUnique();
    }

    public static Member currentUser(Http.Request request) {
        return Member.findByEmail(request.username());
    }

    public boolean canAccessTo(Opening opening) {
        return company.contains(opening);
    }

    public boolean canAccessTo(Applicant applicant) {
        return company.contains(applicant);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void updateLastLoginDate() {
        this.lastLogin = new DateTime();
        update();
    }

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

