package models;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.Http;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static models.Profile.Administrator;
import static models.Profile.Customer;

@Entity
public class Member extends BaseModel<Member> {

    @Constraints.Required
    @Formats.NonEmpty
    private String email;

    @Constraints.Required
    private String firstname;

    @Constraints.Required
    private String lastname;

    @Constraints.Required
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    private DateTime lastLogin;

    public static Model.Finder<String,Member> find = new Model.Finder(String.class, Member.class);

    @Enumerated(EnumType.STRING)
    private Profile profile;

    public Member(String firstname, String lastname, String email, String password, Profile profile) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.profile = profile;
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
        return account.contains(opening);
    }

    public boolean canAccessTo(Applicant applicant) {
        return account.contains(applicant);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullname() {
        return (StringUtils.defaultString(lastname)+" "+StringUtils.defaultString(firstname)).trim();
    }

    public void updateLastLoginDate() {
        this.lastLogin = new DateTime();
        update();
    }

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public boolean isAdministrator() {
        return profile == Administrator;
    }

    public boolean isCustomer() {
        return profile == Customer;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    protected Finder getFinder() {
        return find;
    }
}

