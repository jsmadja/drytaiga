package controllers;

import models.Candidate;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import services.SimpleMail;

import static play.data.Form.form;

public class Application extends Controller {

    public static class Email {
        @Constraints.Required
        public String address;
    }

    public static Result sendEmail() {
        Form<Email> form = form(Email.class).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(views.html.index.render(form, Candidate.findAll()));
        } else {
            Email data = form.get();
            String email = data.address;
            sendEmailTo(email);
            return ok(views.html.index.render(form, Candidate.findAll()));
        }
    }

    private static void sendEmailTo(String email) {
        System.err.println("J'envoie un mail à "+email);
        try {
            new SimpleMail().send(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
