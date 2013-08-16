package models;

import misc.FileSize;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Document extends BaseModel {

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

