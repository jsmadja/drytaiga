package controllers;

import com.avaje.ebean.Ebean;
import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
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
        return ok(index.render(user, form(Position.class)));
    }

    public static Result edit(Long id) {
        Form<Position> form = form(Position.class).fill(
                Position.find.byId(id)
        );
        return ok(
                views.html.positions.update.render(id, form, User.currentUser(request()))
        );
    }

    public static Result read(Long id) {
        return ok(
                views.html.positions.read.render(Position.find.byId(id), User.currentUser(request()), form(Document.class))
        );
    }

    public static Result update(Long id) {
        Form<Position> form = form(Position.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(views.html.positions.update.render(id, form, User.currentUser(request())));
        }
        form.get().update(id);
        flash("success", "Position " + form.get().name + " has been updated");
        return GO_POSITIONS;
    }


    public static Result create() {
        return ok(views.html.positions.create.render(form(Position.class), User.currentUser(request())));
    }

    public static Result save() {
        Form<Position> form = form(Position.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(views.html.positions.create.render(form, User.currentUser(request())));
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

    public static Result upload(Long idPosition) {
        Http.Request request = request();
        Http.RequestBody body = request.body();
        Http.MultipartFormData formData = body.asMultipartFormData();
        Http.MultipartFormData.FilePart picture = formData.getFile("file");
        if (picture != null) {
            S3File s3File = new S3File();
            s3File.file = picture.getFile();
            s3File.name = picture.getFilename();
            s3File.save();
        } else {
            flash("error", "Missing file");
        }
        return redirect(routes.Positions.edit(idPosition));
    }

}
