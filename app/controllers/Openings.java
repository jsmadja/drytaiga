package controllers;

import models.Comment;
import models.Company;
import models.Document;
import models.Opening;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import views.html.openings.create;
import views.html.openings.index;
import views.html.openings.read;

import java.util.ArrayList;
import java.util.List;

import static controllers.routes.Openings;
import static play.data.Form.form;

@Security.Authenticated(Secured.class)
public class Openings extends AjaxController {

    public static Result list() {
        return ok(index.render(user(), openingForm()));
    }

    public static Result create() {
        return ok(create.render(openingForm()));
    }

    public static Result read(Long id) {
        Opening opening = Opening.find.byId(id);
        if(user().canAccessTo(opening)) {
            return ok(read.render(opening, user(), openingForm(opening), documentForm(), commentForm()));
        }
        return forbidden();
    }

    public static Result save() {
        Form<Opening> form = openingForm().bindFromRequest();
        if (form.value().isDefined()) {
            checkNameUnicity(form, form.get());
        }
        if (form.hasErrors()) {
            return badRequest(form);
        }
        Opening opening = form.get();
        Company company = company();
        company.addOpening(opening);
        company.update();
        return ok(Openings.read(opening.getId()));
    }

    public static Result update(Long id) {
        Form<Opening> form = openingForm().bindFromRequest();
        if (form.value().isDefined()) {
            boolean nameHasChanged = !Opening.find.byId(id).hasName(form.get().getName());
            if(nameHasChanged) {
                checkNameUnicity(form, form.get());
            }
        }
        if (form.hasErrors()) {
            return badRequest(form);
        }
        Opening opening = Opening.find.byId(id);
        if(user().canAccessTo(opening)) {
            opening.setName(form.data().get("name"));
            opening.update();
            return ok(Openings.read(id));
        }
        return forbidden();
    }

    private static void checkNameUnicity(Form<Opening> form, Opening opening) {
        if (company().containsOpeningWithName(opening.getName())) {
            addError(form, "name", "openings.form.name.alreadyexist", opening.getName());
        }
    }

    private static Form<Opening> openingForm() {
        return form(Opening.class);
    }

    private static Form<Opening> openingForm(Opening opening) {
        return form(Opening.class).fill(opening);
    }

    private static Form<Document> documentForm() {
        return form(Document.class);
    }

    private static Form<Comment> commentForm() {
        return form(Comment.class);
    }

    public static Result a() {
        List<String> values = new ArrayList<String>();
        values.add("A");
        values.add("B");
        values.add("C");
        return toJson(values);
    }

}
