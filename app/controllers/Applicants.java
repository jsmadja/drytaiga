package controllers;

import play.mvc.Result;
import play.mvc.Security;
import views.html.applicants.index;

@Security.Authenticated(Secured.class)
public class Applicants extends AjaxController {

    public static Result list() {
        return ok(index.render(user()));
    }

}
