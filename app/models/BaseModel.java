package models;

import com.avaje.ebean.annotation.CreatedTimestamp;
import play.mvc.PathBindable;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public abstract class BaseModel<T extends BaseModel<T>> extends Model implements PathBindable<T> {

    @Id
    protected Long id;

    @CreatedTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne
    protected Account account;

    protected abstract Finder getFinder();

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseModel baseModel = (BaseModel) o;
        return !(id != null ? !id.equals(baseModel.id) : baseModel.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public T bind(String key, String value) {
        return (T) getFinder().byId(Long.valueOf(value));
    }

    @Override
    public String unbind(String s) {
        return id.toString();
    }

    @Override
    public String javascriptUnbind() {
        return null;
    }
}
