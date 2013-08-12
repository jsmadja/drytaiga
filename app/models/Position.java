package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Position extends Model implements Documentable {

    @Id
    private Long id;

    @Constraints.Required
    public String name;

    @ManyToMany(mappedBy = "positions", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    public List<Candidate> candidates = new ArrayList<Candidate>();

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<Document>();

    @ManyToOne
    public Company company;

    public static Model.Finder<Long,Position> find = new Model.Finder(Long.class, Position.class);

    public Position(String name) {
        this.name = name;
    }

    public void addCandidate(Candidate candidate) {
        this.candidates.add(candidate);
        candidate.addPosition(this);
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

    public boolean hasName(String positionName) {
        return name.equals(positionName);
    }
}

