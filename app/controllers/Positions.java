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
        return ok(read.render(position, user(), positionForm(position), documentForm()));
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
            boolean nameHasChanged = !Position.find.byId(id).hasName(form.get().name);
            if(nameHasChanged) {
                checkNameUnicity(form, form.get());
            }
        }
        if (form.hasErrors()) {
            return badRequest(form);
        }
        Position position = Position.find.byId(id);
        position.name = form.data().get("name");
        position.update();
        return ok(Positions.read(id));
    }

    private static void checkNameUnicity(Form<Position> form, Position position) {
        if (company().containsPositionWithName(position.name)) {
            addError(form, "name", "positions.form.name.alreadyexist", position.name);
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

}
