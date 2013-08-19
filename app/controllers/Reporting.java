package controllers;

import controllers.security.SecuredController;
import play.mvc.Result;
import views.html.reporting.index;

public class Reporting extends SecuredController {

    public static Result index() {
        return ok(index.render(user()));
    }

}
