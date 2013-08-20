package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;

@Entity
public class Company extends BaseModel<Company> {

    @Constraints.Required
    private String name;

    public static Model.Finder<Long, Company> find = new Model.Finder(Long.class, Company.class);

    public Company(String name, Account account) {
        this.name = name;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    @Override
    protected Finder getFinder() {
        return find;
    }
}