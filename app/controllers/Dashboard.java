package controllers;

import controllers.security.SecuredController;
import models.Member;
import play.mvc.Result;

import static models.Member.currentUser;

public class Dashboard extends SecuredController {

    public static Result index() {
        Member user = currentUser(request());
        return ok(views.html.dashboard.render(user));
    }

}
