package controllers;

import models.Company;
import models.User;
import play.mvc.Controller;

import static models.User.currentUser;

public class SecuredController extends Controller {

    protected static Company company() {
        return user().company;
    }

    protected static User user() {
        return currentUser(request());
    }
}
