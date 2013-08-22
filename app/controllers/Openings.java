package controllers;

import misc.table.ColumnValueResolver;
import misc.table.Html;
import models.*;
import play.data.Form;
import play.mvc.Result;
import views.html.openings.create;
import views.html.openings.index;
import views.html.openings.read;

import java.util.ArrayList;
import java.util.List;

import static controllers.routes.Openings;
import static misc.table.Html.url;
import static misc.table.TableFilter.createNode;
import static play.data.Form.form;

public class Openings extends AjaxController {

    public static Result list() {
        return ok(index.render(user(), openingForm()));
    }

    public static Result all() {
        return ok(createNode(request(), Opening.find.query(), new ColumnValueResolver<Opening>() {
            @Override
            public String label(Opening opening) {
                return url(routes.Openings.read(opening), value(opening).toString());
            }

            @Override
            public Comparable value(Opening opening) {
                return opening.getName();
            }
        }));
    }

    public static Result create() {
        return ok(create.render(openingForm()));
    }

    public static Result read(Opening opening) {
        if (user().canAccessTo(opening)) {
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
        Account account = account();
        account.addOpening(opening);
        account.update();
        return ok(Openings.read(opening));
    }

    public static Result update(Opening opening) {
        Form<Opening> form = openingForm().bindFromRequest();
        if (form.value().isDefined()) {
            boolean nameHasChanged = !opening.hasName(form.get().getName());
            if (nameHasChanged) {
                checkNameUnicity(form, form.get());
            }
        }
        if (form.hasErrors()) {
            return badRequest(form);
        }
        if (user().canAccessTo(opening)) {
            opening.setName(form.data().get("name"));
            opening.update();
            return ok(Openings.read(opening));
        }
        return forbidden();
    }

    private static void checkNameUnicity(Form<Opening> form, Opening opening) {
        if (account().containsOpeningWithName(opening.getName())) {
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
