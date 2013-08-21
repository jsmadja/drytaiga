package controllers;

import controllers.security.SecuredController;
import play.mvc.Result;
import views.html.accounts.index;

public class Accounts extends SecuredController {

    public static Result list() {
        return ok(index.render(user()));
    }

    public static int percent(Long up, Long down) {
        return (int) (((double) up / down) * 100);
    }

}
