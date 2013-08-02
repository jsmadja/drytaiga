package controllers;

import com.avaje.ebean.Ebean;
import com.google.common.io.Files;
import models.Candidate;
import models.Company;
import models.Mail;
import models.S3File;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import plugins.MailerPlugin;
import plugins.S3Plugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static play.data.Form.form;

public class Application extends Controller {

    public static class Email {
        @Constraints.Required
        public String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static Result sendEmail() {
        Form<Email> form = form(Email.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(views.html.index.render(form, Company.findAll()));
        } else {
            Email data = form.get();
            String email = data.address;

            Company company = Company.findAll().get(0);
            company.positions.get(0).addCandidate(new Candidate("Robert", "Martin", "rmartin@gmail.com"));
            Ebean.update(company);

            sendEmailTo(email);
            storeFile(email);
            return ok(views.html.index.render(form, Company.findAll()));
        }
    }

    private static void storeFile(String content) {
        if (S3Plugin.isEnabled()) {
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
    }

    private static void sendEmailTo(String email) {
        if (MailerPlugin.isEnabled()) {
            try {
                new Mail(email).send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
