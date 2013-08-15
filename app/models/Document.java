package models;

import misc.FileSize;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Document extends Model {

    @Id
    private Long id;

    @Constraints.Required
    private String name;

    @Constraints.Required
    private String url;

    @Constraints.Required
    private Long size;

    @Constraints.Required
    private String contentType;

    @ManyToOne
    private Position position;

    @ManyToOne
    private Applicant applicant;

    @OneToOne
    private Account account;

    public Document(String name, String url, Long size, String contentType) {
        this.name = name;
        this.url = url;
        this.size = size;
        this.contentType = contentType;
    }

    public void attachTo(Position position) {
        this.position = position;
        this.account = position.getAccount();
    }

    public String sizeToString() {
        return new FileSize(size).toString();
    }

    public Long getSize() {
        return size;
    }

    public String getContentType() {
        return contentType;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}

