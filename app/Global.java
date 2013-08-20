import com.avaje.ebean.Ebean;
import misc.PrettyStacktrace;
import models.Account;
import models.Mail;
import org.slf4j.MDC;
import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import plugins.MailerPlugin;

import java.lang.reflect.Method;

import static java.lang.String.format;
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
    public Result onError(Http.RequestHeader requestHeader, Throwable throwable) {
        sendEmailWithStacktrace(requestHeader, throwable);
        return super.onError(requestHeader, throwable);
    }

    private void sendEmailWithStacktrace(Http.RequestHeader requestHeader, Throwable throwable) {
        StringBuilder body = new StringBuilder();
        body.append(format("user: %s", email())).append("<br/>");
        body.append(format("%s %s", requestHeader.method(), requestHeader.uri())).append("<br/>");
        body.append(new PrettyStacktrace(throwable).toString());
        Mail mail = new Mail("julien.smadja@gmail.com", "Une erreur est survenue", body.toString(), body.toString());
        mail.send();
    }

    private String email() {
        return Http.Context.current().session().get("email");
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