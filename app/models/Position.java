package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Position extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @ManyToMany(mappedBy = "positions", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    public List<Candidate> candidates = new ArrayList<Candidate>();

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
}

