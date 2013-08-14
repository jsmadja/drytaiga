import play.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {
        public static void insert(Application app) {
            if(Ebean.find(Account.class).findRowCount() == 0) {
                Account account = new Account(AccountType.EstablishedCompany);
                Ebean.save(account);

                Member user = new Member("John", "Doe", "jdoe@mycompany.com", "password");
                Ebean.save(user);

                account.setOwner(user);
                Ebean.update(account);

                Company company = new Company("My Company", account);
                company.addMember(user);
                Ebean.save(company);
            }
        }
    }

}