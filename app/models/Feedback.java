package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Feedback extends BaseModel<Feedback> {

    public static Model.Finder<String, Feedback> find = new Model.Finder(String.class, Feedback.class);

    @Lob
    @Constraints.Required
    private String text;

    @ManyToOne
    private Member author;

    private FeedbackType feedbackType;

    public Feedback() {
    }

    public Feedback(String text, FeedbackType feedbackType, Member author) {
        this.text = text;
        this.feedbackType = feedbackType;
        this.author = author;
        this.account = author.account;
    }

    @Override
    protected Finder getFinder() {
        return find;
    }

    public String getText() {
        return text;
    }

    public Member getAuthor() {
        return author;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }
}
