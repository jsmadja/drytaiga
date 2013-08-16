package controllers;

import com.avaje.ebean.Ebean;
import models.Comment;
import models.Commentable;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Result;
import play.mvc.Security;

import java.lang.reflect.Field;
import java.util.Map;

import static play.data.Form.form;

@Security.Authenticated(Secured.class)
public class Comments extends SecuredController {

    public static Result create(Long id, String clazz, String callback) {
        return ok(views.html.comments.create.render(form(Comment.class), id, clazz, callback));
    }

    public static Result save(Long id, String clazz, String callback) {
        try {
            Map<String, String> data = new Form(Comment.class).bindFromRequest().data();
            Comment comment = new Comment(data.get("text"), user());
            Commentable commentable = findCommentable(id, clazz);
            commentable.addComment(comment);
            Ebean.update(commentable);
            return redirect(callback);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Commentable findCommentable(Long id, String clazz) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Field findField = Class.forName(clazz).getField("find");
        Model.Finder<Long, Commentable> find = (Model.Finder<Long, Commentable>) findField.get(clazz);
        return find.byId(id);
    }

}
