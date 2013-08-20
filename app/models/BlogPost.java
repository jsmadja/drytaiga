package models;

import com.avaje.ebean.annotation.CreatedTimestamp;
import play.db.ebean.Model;
import play.mvc.PathBindable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class BlogPost extends Model implements PathBindable<BlogPost> {

    @Id
    private Long id;

    private String title;

    @CreatedTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    private String text;

    public static Model.Finder<Long, BlogPost> find = new Model.Finder(Long.class, BlogPost.class);

    public BlogPost() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public BlogPost(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogPost blogPost = (BlogPost) o;
        return !(id != null ? !id.equals(blogPost.id) : blogPost.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public BlogPost bind(String key, String text) {
        return find.byId(Long.valueOf(text));
    }

    @Override
    public String unbind(String key) {
        return id.toString();
    }

    @Override
    public String javascriptUnbind() {
        return null;
    }
}
