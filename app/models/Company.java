package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;

@Entity
public class Company extends BaseModel {

    @Constraints.Required
    private String name;

    public static Model.Finder<Long, Company> find = new Model.Finder(Long.class, Company.class);

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}