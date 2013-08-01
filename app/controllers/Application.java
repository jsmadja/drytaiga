package controllers;

import com.google.common.io.Files;
import models.Candidate;
import models.S3File;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import services.SimpleMail;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static play.data.Form.form;

public class Application extends Controller {

    public static class Email {
        @Constraints.Required
        public String address;
    }

    public static Result sendEmail() {
        Form<Email> form = form(Email.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(views.html.index.render(form, Candidate.findAll()));
        } else {
            Email data = form.get();
            String email = data.address;
            sendEmailTo(email);
            storeFile(email);
            return ok(views.html.index.render(form, Candidate.findAll()));
        }
    }

    private static void storeFile(String content) {
        try {
            S3File s3File = new S3File();
            s3File.name = UUID.randomUUID().toString() + ".txt";
            File to = new File("/tmp/" + s3File.name);
            Files.write(content.getBytes(), to);
            s3File.file = to;
            s3File.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendEmailTo(String email) {
        try {
            new SimpleMail().send(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
