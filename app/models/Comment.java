package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Comment extends BaseModel {

    @Constraints.Required
    private String text;

    @ManyToOne
    private Opening opening;

    @ManyToOne
    private Applicant applicant;

    @ManyToOne
    private Member author;

    public Comment(String text, Member author) {
        this.text = text;
        this.author = author;
    }

    public void attachTo(Opening opening) {
        this.opening = opening;
    }

    public String getText() {
        return text;
    }

    public Member getAuthor() {
        return author;
    }

    public Date getDate() {
        return getCreatedAt();
    }

    public void attachTo(Applicant applicant) {
        this.applicant = applicant;
    }
}

