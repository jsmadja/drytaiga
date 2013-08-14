import org.slf4j.MDC;
import play.*;

import com.avaje.ebean.*;

import models.*;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;

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

    @Override
    public Result onError(Http.RequestHeader requestHeader, Throwable throwable) {
        return super.onError(requestHeader, throwable);
    }
}