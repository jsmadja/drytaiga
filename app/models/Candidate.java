package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="candidate")
public class Candidate {

    @Constraints.Required
    public String firstname;

    @Constraints.Required
    public String lastname;

    @Id
    @Constraints.Required
    @Formats.NonEmpty
    public String email;

    public static Model.Finder<String,Candidate> find = new Model.Finder(String.class, Candidate.class);

    public Candidate(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public static List<Candidate> findAll() {
        return find.all();
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
}
