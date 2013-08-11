package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Candidate extends Model implements Documentable {

    @Id
    public Long id;

    @Constraints.Required
    public String firstname;

    @Constraints.Required
    public String lastname;

    @Constraints.Required
    @Formats.NonEmpty
    public String email;

    @ManyToMany
    public List<Position> positions = new ArrayList<Position>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<Document>();

    public Candidate(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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
}
