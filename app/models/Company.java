package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company extends Model {

    @Id
    public Long id;

    public String name;

    @OneToMany(cascade = CascadeType.ALL)
    public List<User> members = new ArrayList<User>();

    @OneToMany(cascade = CascadeType.ALL)
    public List<Position> positions = new ArrayList<Position>();

    private Company(String name) {
        this.name = name;
    }

    public static Company create(String name) {
        Company company = new Company(name);
        company.save();
        return company;
    }

    public void addMember(User user) {
        user.company = this;
        members.add(user);
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public static Model.Finder<Long, Company> find = new Model.Finder(Long.class, Company.class);
    public static List<Company> findAll() {
        return find.all();
    }

    public boolean hasMember(String email) {
        for (User member : members) {
            if(member.hasEmail(email)) {
                return true;
            }
        }
        return false;
    }
}