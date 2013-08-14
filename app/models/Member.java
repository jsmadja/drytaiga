package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.Http;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member extends Model {

    @Id
    public Long id;

    @Constraints.Required
    @Formats.NonEmpty
    public String email;

    @Constraints.Required
    public String firstname;

    @Constraints.Required
    public String lastname;

    @Constraints.Required
    public String password;

    @ManyToOne
    public Company company;

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

    public boolean canAccessTo(Position position) {
        return company.contains(position);
    }
}

