import play.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {
        public static void insert(Application app) {
            if(Ebean.find(Candidate.class).findRowCount() == 0) {
                Company company = Company.create("xebia");
                User user = new User("Julien", "Smadja", "jsmadja@xebia.fr", "ogdsoiugdosfsdo");
                Candidate candidate = new Candidate("Martin", "Odersky", "julien.smadja@gmail.com");
                Position position = new Position("Developer");

                company.addMember(user);
                company.addPosition(position);
                position.addCandidate(candidate);
                candidate.addDocument(new Document("/tmp/cv.pdf"));

                Ebean.update(company);
            }
        }
    }

}