package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Comment extends BaseModel<Comment> {

    @Constraints.Required
    private String text;

    @ManyToOne
    private Opening opening;

    @ManyToOne
    private Applicant applicant;

    @ManyToOne
    private Member author;

    public static Model.Finder<Long, Comment> find = new Model.Finder(Long.class, Comment.class);

    public Comment(String text, Member author) {
        this.text = text;
        this.author = author;
        this.account = author.getAccount();
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

    @Override
    protected Finder getFinder() {
        return find;
    }
}

