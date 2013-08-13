import play.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {
        public static void insert(Application app) {
            if(Ebean.find(User.class).findRowCount() == 0) {
                Company company = Company.create("My Company");
                User user = new User("John", "Doe", "jdoe@mycompany.com", "password");
                company.addMember(user);
                Ebean.update(company);
            }
        }
    }

}