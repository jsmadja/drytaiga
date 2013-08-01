import play.*;
import play.libs.*;

import java.util.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {
        public static void insert(Application app) {
            if(Ebean.find(Candidate.class).findRowCount() == 0) {
                Ebean.save(new Candidate("Julien", "Smadja", "julien.smadja@gmail.com"));
            }
        }
    }

}