package controllers;

import models.Company;
import models.Member;
import play.mvc.Controller;

import static models.Member.currentUser;

public class SecuredController extends Controller {

    protected static Company company() {
        return user().company;
    }

    protected static Member user() {
        return currentUser(request());
    }
}
