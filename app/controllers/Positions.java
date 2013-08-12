package controllers;

import com.avaje.ebean.Ebean;
import models.Company;
import models.Document;
import models.Position;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.positions.create;
import views.html.positions.index;
import views.html.positions.read;

import static controllers.routes.Positions;
import static models.User.currentUser;
import static play.data.Form.form;

@Security.Authenticated(Secured.class)
public class Positions extends Controller {

    public static Result list() {
        return ok(index.render(user(), form(Position.class)));
    }

    public static Result create() {
        return ok(create.render(form(Position.class), user()));
    }

    public static Result read(Long id) {
        Position position = Position.find.byId(id);
        return ok(read.render(position, user(), positionForm(position), documentForm()));
    }

    public static Result update(Long id) {
        Position position = Position.find.byId(id);
        Form<Position> form = form(Position.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(read.render(position, user(), positionForm(position), documentForm()));
        }
        position.name = form.data().get("name");
        position.update();
        return redirect(Positions.read(id));
    }

    public static Result save() {
        Form<Position> form = form(Position.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(create.render(form, user()));
        }
        Company company = user().company;
        company.addPosition(form.get());
        Ebean.update(company);
        return redirect(Positions.list());
    }

    /**
     * forms
     */
    private static Form<Position> positionForm(Position position) {
        return form(Position.class).fill(position);
    }

    private static Form<Document> documentForm() {
        return form(Document.class);
    }

    private static User user() {
        return currentUser(request());
    }

}
