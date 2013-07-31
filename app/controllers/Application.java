package controllers;

import models.Candidate;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

    public static Result candidates() {
        return ok(views.html.index.render(Candidate.findAll()));
    }

}
