package controllers.security;

import models.Account;
import models.Member;
import play.mvc.Controller;
import play.mvc.Security;

import static models.Member.currentUser;

@Security.Authenticated(Secured.class)
public abstract class SecuredController extends Controller {

    protected static Account account() {
        return user().getAccount();
    }

    protected static Member user() {
        return currentUser(request());
    }
}
