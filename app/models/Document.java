package models;

import misc.FileSize;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Document extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String url;

    @Constraints.Required
    public Long size;

    @Constraints.Required
    public String contentType;

    @ManyToOne
    private Position position;

    @ManyToOne
    private Candidate candidate;

    public Document(String name, String url, Long size, String contentType) {
        this.name = name;
        this.url = url;
        this.size = size;
        this.contentType = contentType;
    }

    public void attachTo(Position position) {
        this.position = position;
    }

    public String sizeToString() {
        return new FileSize(size).toString();
    }
}

