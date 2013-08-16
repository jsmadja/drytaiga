package controllers;

import models.Account;
import models.Company;
import models.Member;
import play.mvc.Controller;

import static models.Member.currentUser;

public class SecuredController extends Controller {

    protected static Account account() {
        return user().getAccount();
    }

    protected static Member user() {
        return currentUser(request());
    }
}
