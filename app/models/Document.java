package models;

import formatters.BytesFormatter;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Document extends BaseModel<Document> {

    @Constraints.Required
    private String name;

    @Constraints.Required
    private String url;

    @Constraints.Required
    private Long size;

    @Constraints.Required
    private String contentType;

    @ManyToOne
    private Opening opening;

    @ManyToOne
    private Applicant applicant;

    public static Model.Finder<Long, Document> find = new Model.Finder(Long.class, Document.class);

    public Document(String name, String url, Long size, String contentType) {
        this.name = name;
        this.url = url;
        this.size = size;
        this.contentType = contentType;
    }

    public void attachTo(Opening opening) {
        this.opening = opening;
        this.account = opening.getAccount();
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

    @Override
    protected Finder getFinder() {
        return find;
    }
}

