import com.avaje.ebean.Ebean;
import models.Account;
import org.slf4j.MDC;
import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;

import static views.TestValues.createAccount;
import static views.TestValues.createAdministrator;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {
        public static void insert(Application app) {
            if (Ebean.find(Account.class).findRowCount() == 0) {
                createAdministrator();
                for (int accountNumber = 0; accountNumber <= 10/*26*/; accountNumber++) {
                    createAccount(accountNumber);
                }
            }
        }

    }

    @Override
    public Action onRequest(final Http.Request request, Method actionMethod) {
        return new Action.Simple() {
            public Result call(Http.Context ctx) throws Throwable {
                MDC.put("user", ctx.session().get("email"));
                MDC.put("uri", request.uri());
                return delegate.call(ctx);
            }
        };
    }

}