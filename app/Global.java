import play.*;
import play.libs.*;

import java.util.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        //InitialData.insertFromFile(app);
        InitialData.insert(app);

    }

    static class InitialData {
        public static void insertFromFile(Application app) {
            if(Ebean.find(Candidate.class).findRowCount() == 0) {
                Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");
                Ebean.save(all.get("candidates"));
            }
        }

        public static void insert(Application app) {
            if(Ebean.find(Candidate.class).findRowCount() == 0) {
                Ebean.save(new Candidate("Julien", "Smadja", "julien.smadja@gmail.com"));
            }
        }
    }

}