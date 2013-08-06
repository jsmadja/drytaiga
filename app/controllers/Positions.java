package controllers;

import com.avaje.ebean.Ebean;
import models.Company;
import models.Position;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.positions.index;

import static play.data.Form.form;

@Security.Authenticated(Secured.class)
public class Positions extends Controller {

    public static Result GO_POSITIONS = redirect(
            routes.Positions.list()
    );

    public static Result list() {
        User user = User.currentUser(request());
        return ok(index.render(user));
    }

    /**
     * Display the 'edit form' of a existing Computer.
     *
     * @param id Id of the computer to edit
     */
    public static Result edit(Long id) {
        Form<Position> form = form(Position.class).fill(
                Position.find.byId(id)
        );
        return ok(
                views.html.positions.editForm.render(id, form, User.currentUser(request()))
        );
    }

    public static Result update(Long id) {
        Form<Position> form = form(Position.class).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(views.html.positions.editForm.render(id, form, User.currentUser(request())));
        }
        form.get().update(id);
        flash("success", "Position " + form.get().name + " has been updated");
        return GO_POSITIONS;
    }


    public static Result create() {
        return ok(views.html.positions.createForm.render(form(Position.class), User.currentUser(request())));
    }

    public static Result save() {
        Form<Position> form = form(Position.class).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(views.html.positions.createForm.render(form, User.currentUser(request())));
        }
        Company company = User.currentUser(request()).company;
        company.addPosition(form.get());
        Ebean.update(company);

        flash("success", "Position " + form.get().name + " has been created");
        return GO_POSITIONS;
    }

    public static Result delete(Long idPosition) {
        Position position = Position.find.ref(idPosition);
        if (Secured.isMemberOf(position.company)) {
            position.delete();
            return GO_POSITIONS;
        } else {
            return forbidden();
        }
    }

}
