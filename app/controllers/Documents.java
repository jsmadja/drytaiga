package controllers;

import com.avaje.ebean.Ebean;
import models.Document;
import models.Position;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import plugins.StoragePlugin;

import static java.lang.String.format;
import static play.data.Form.form;

public class Documents extends Controller {

    public static Result create(Long idPosition) {
        return ok(views.html.documents.create.render(form(Document.class), idPosition));
    }

    public static Result save(Long idPosition) {
        Position position = Position.find.byId(idPosition);
        Document document = storeDocumentFor(position);
        position.addDocument(document);
        Ebean.update(position);
        return redirect(routes.Positions.read(idPosition));
    }

    private static Document storeDocumentFor(Position position) {
        Http.MultipartFormData.FilePart filePart = request().body().asMultipartFormData().getFile("file");
        return StoragePlugin.store(filePart.getFile(), getPath(position), filePart.getContentType());
    }

    private static String getPath(Position position) {
        return format("companies/%d/positions/%d/%s", position.company.id, position.getId(), request().body().asMultipartFormData().getFile("file").getFilename());
    }

}
