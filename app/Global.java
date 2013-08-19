import com.avaje.ebean.Ebean;
import com.google.common.collect.FluentIterable;
import models.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import play.Application;
import play.GlobalSettings;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {
        public static void insert(Application app) {
            if (Ebean.find(Account.class).findRowCount() == 0) {
                Account account = new Account(AccountType.EstablishedCompany);
                Ebean.save(account);

                Member user = new Member("John", "Doe", "jdoe@mycompany.com", "password");
                Ebean.save(user);

                account.setOwner(user);
                account.setCompany(new Company("My Company", account));
                account.addMember(user);

                Opening opening = new Opening("Programmer");
                opening.addComment(new Comment("First comment on opening", user));
                account.addOpening(opening);

                for (int i = 0; i < 100; i++) {
                    Applicant applicant = new Applicant("Bob", "Lennon #"+i, "blennon"+i+"@gmail.com", account);
                    applicant.addComment(new Comment("First comment on applicant", user));
                    int index = Integer.parseInt(randomNumeric(1)) % ApplianceStatus.values().length;
                    ApplianceStatus randomApplianceStatus = ApplianceStatus.values()[index];
                    applicant.updateStatus(randomApplianceStatus);
                    opening.addApplicant(applicant);
                }

                Ebean.update(account);
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