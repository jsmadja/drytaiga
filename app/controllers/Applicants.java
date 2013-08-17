package controllers;

import models.ApplianceStatus;
import models.Applicant;
import models.Comment;
import models.Document;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import views.html.applicants.index;
import views.html.applicants.read;

import static models.Applicant.find;
import static play.data.Form.form;

@Security.Authenticated(Secured.class)
public class Applicants extends AjaxController {

    public static Result list() {
        return ok(index.render(user()));
    }

    public static Result read(Long id) {
        Applicant applicant = find.byId(id);
        if (user().canAccessTo(applicant)) {
            return ok(read.render(applicant, user(), documentForm(), commentForm()));
        }
        return forbidden();
    }

    public static Result changeApplianceStatus(Long id) {
        Applicant applicant = find.byId(id);
        if (user().canAccessTo(applicant)) {
            ApplianceStatus applianceStatus = ApplianceStatus.values()[Integer.valueOf(form().bindFromRequest().data().get("applianceStatus"))];
            applicant.updateStatus(applianceStatus);
            applicant.update();
            return ok();
        }
        return forbidden();
    }

    private static Form<Document> documentForm() {
        return form(Document.class);
    }

    private static Form<Comment> commentForm() {
        return form(Comment.class);
    }

}
