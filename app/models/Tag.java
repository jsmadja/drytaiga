package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Tag extends BaseModel<Tag> {

    @Constraints.Required
    private String text;

    @ManyToOne
    private Applicant applicant;

    public static Model.Finder<Long, Tag> find = new Model.Finder(Long.class, Tag.class);

    public Tag(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    public void attachTo(Applicant applicant) {
        this.applicant = applicant;
        this.account = applicant.getAccount();
    }

    public boolean hasText(String text) {
        return this.text.equals(text);
    }

    @Override
    protected Finder getFinder() {
        return find;
    }
}
