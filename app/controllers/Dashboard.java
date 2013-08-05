package controllers;


import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import static models.User.currentUser;

@Security.Authenticated(Secured.class)
public class Dashboard extends Controller {
    public static Result index() {
        User user = currentUser(request());
        return ok(views.html.dashboard.render(user));
    }

}
