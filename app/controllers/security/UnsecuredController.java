package controllers.security;

import models.Member;
import play.mvc.Controller;

public abstract class UnsecuredController extends Controller {

    protected static Member user() {
        String email = ctx().session().get("email");
        if (email != null) {
            return Member.findByEmail(email);
        }
        return null;
    }
}
