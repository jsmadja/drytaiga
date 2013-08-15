package controllers;

import models.Company;
import models.Document;
import models.Position;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import views.html.positions.create;
import views.html.positions.index;
import views.html.positions.read;

import java.util.ArrayList;
import java.util.List;

import static controllers.routes.Positions;
import static play.data.Form.form;

@Security.Authenticated(Secured.class)
public class Positions extends AjaxController {

    public static Result list() {
        return ok(index.render(user(), positionForm()));
    }

    public static Result create() {
        return ok(create.render(positionForm()));
    }

    public static Result read(Long id) {
        Position position = Position.find.byId(id);
        if(user().canAccessTo(position)) {
            return ok(read.render(position, user(), positionForm(position), documentForm()));
        }
        return forbidden();
    }

    public static Result save() {
        Form<Position> form = positionForm().bindFromRequest();
        if (form.value().isDefined()) {
            checkNameUnicity(form, form.get());
        }
        if (form.hasErrors()) {
            return badRequest(form);
        }
        Position position = form.get();
        Company company = company();
        company.addPosition(position);
        company.update();
        return ok(Positions.read(position.getId()));
    }

    public static Result update(Long id) {
        Form<Position> form = positionForm().bindFromRequest();
        if (form.value().isDefined()) {
            boolean nameHasChanged = !Position.find.byId(id).hasName(form.get().getName());
            if(nameHasChanged) {
                checkNameUnicity(form, form.get());
            }
        }
        if (form.hasErrors()) {
            return badRequest(form);
        }
        Position position = Position.find.byId(id);
        if(user().canAccessTo(position)) {
            position.setName(form.data().get("name"));
            position.update();
            return ok(Positions.read(id));
        }
        return forbidden();
    }

    private static void checkNameUnicity(Form<Position> form, Position position) {
        if (company().containsPositionWithName(position.getName())) {
            addError(form, "name", "positions.form.name.alreadyexist", position.getName());
        }
    }

    private static Form<Position> positionForm() {
        return form(Position.class);
    }

    private static Form<Position> positionForm(Position position) {
        return form(Position.class).fill(position);
    }

    private static Form<Document> documentForm() {
        return form(Document.class);
    }

    public static Result a() {
        List<String> values = new ArrayList<String>();
        values.add("A");
        values.add("B");
        values.add("C");
        return toJson(values);
    }

}
