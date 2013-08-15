package controllers;

import com.avaje.ebean.Ebean;
import models.Document;
import models.Documentable;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import plugins.StoragePlugin;

import java.lang.reflect.Field;

import static java.lang.String.format;
import static play.data.Form.form;

public class Documents extends Controller {

    public static Result create(Long id, String clazz, String callback) {
        return ok(views.html.documents.create.render(form(Document.class), id, clazz, callback));
    }

    public static Result save(Long id, String clazz, String callback) {
        try {
            Documentable documentable = findDocumentable(id, clazz);
            Document document = storeDocumentFor(documentable);
            documentable.addDocument(document);
            Ebean.update(documentable);
            return redirect(callback);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Documentable findDocumentable(Long id, String clazz) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Field findField = Class.forName(clazz).getField("find");
        Model.Finder<Long, Documentable> find = (Model.Finder<Long, Documentable>) findField.get(clazz);
        return find.byId(id);
    }

    private static Document storeDocumentFor(Documentable documentable) {
        Http.MultipartFormData.FilePart filePart = request().body().asMultipartFormData().getFile("file");
        return StoragePlugin.store(filePart.getFile(), documentable.getFilePath(filePart), filePart.getContentType());
    }

}
