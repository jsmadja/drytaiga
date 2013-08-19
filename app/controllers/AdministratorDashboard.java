package controllers;

import controllers.security.SecuredController;
import models.Account;
import models.Member;
import play.mvc.Result;
import views.html.administratordashboard;

import java.util.Collection;

import static models.Member.currentUser;

public class AdministratorDashboard extends SecuredController {

    public static Result index() {
        Member user = currentUser(request());
        if (!user.isAdministrator()) {
            return badRequest();
        }
        return ok(administratordashboard.render(user));
    }

    public static Collection<Account> getAccounts() {
        return Account.find.all();
    }

}
