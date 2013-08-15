package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.pricing;

public class Pricing extends Controller {

    public static Result index() {
        return ok(pricing.render(null));
    }
}
