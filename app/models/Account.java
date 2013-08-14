package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account extends Model {

    @Id
    public Long id;

    private AccountType accountType;

    @OneToOne
    private Member owner;

    private AccountStatus status;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.status = AccountStatus.Active;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }
}
