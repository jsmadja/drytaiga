package models;

import misc.FileSize;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    public List<Member> members = new ArrayList<Member>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    public List<Position> positions = new ArrayList<Position>();

    @OneToOne
    private Account account;

    public Company(String name, Account account) {
        this.name = name;
        this.account = account;
    }

    public void addMember(Member user) {
        user.company = this;
        members.add(user);
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public static Model.Finder<Long, Company> find = new Model.Finder(Long.class, Company.class);

    public static boolean isMember(Long company, String user) {
        return find.where()
                .eq("members.email", user)
                .eq("id", company)
                .findRowCount() > 0;
    }

    public static boolean isMember(Company company, String user) {
        return isMember(company.id, user);
    }

    public String getUsedSpace() {
        long usedSpace = 0;
        List<Documentable> documentables = new ArrayList<Documentable>();
        documentables.addAll(positions);
        for (Documentable documentable : documentables) {
            List<Document> documents = documentable.getDocuments();
            for (Document document : documents) {
                usedSpace += document.size;
            }
        }
        return new FileSize(usedSpace).toString();
    }

    public long getTotalSpace() {
        return 100;
    }

    public int getNumDocuments() {
        int numDocuments = 0;
        List<Documentable> documentables = new ArrayList<Documentable>();
        documentables.addAll(positions);
        for (Documentable documentable : documentables) {
            List<Document> documents = documentable.getDocuments();
            numDocuments += documents.size();
        }
        return numDocuments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Company company = (Company) o;

        if (!name.equals(company.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public boolean containsPositionWithName(String positionName) {
        for (Position position : positions) {
            if(position.hasName(positionName)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Position position) {
        return positions.contains(position);
    }
}