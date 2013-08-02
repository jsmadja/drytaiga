package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Document extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    public Document(String name) {
        this.name = name;
    }

}

