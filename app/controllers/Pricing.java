package controllers;

import controllers.security.UnsecuredController;
import play.mvc.Result;
import views.html.pricing;

public class Pricing extends UnsecuredController {

    public static Result index() {
        return ok(pricing.render(user()));
    }
}
