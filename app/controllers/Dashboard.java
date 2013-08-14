package controllers;

import models.Member;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import static models.Member.currentUser;

@Security.Authenticated(Secured.class)
public class Dashboard extends Controller {

    public static Result index() {
        Member user = currentUser(request());
        return ok(views.html.dashboard.render(user));
    }

}
