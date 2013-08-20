package controllers;

import com.avaje.ebean.Ebean;
import controllers.security.SecuredController;
import models.Comment;
import models.Tag;
import models.Taggable;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Result;

import java.lang.reflect.Field;
import java.util.Map;

public class Tags extends SecuredController {

    public static Result save(Long id, String clazz) {
        Map<String, String> data = new Form(Comment.class).bindFromRequest().data();
        Tag tag = new Tag(data.get("text"));
        Taggable taggable = findTaggable(id, clazz);
        taggable.addTag(tag);
        Ebean.update(taggable);
        return ok();
    }

    public static Result delete(Long id, String clazz) {
        Map<String, String> data = new Form(Comment.class).bindFromRequest().data();
        String text = data.get("text");
        Taggable taggable = findTaggable(id, clazz);
        Tag tagToRemove = null;
        for (Tag tag : taggable.getTags()) {
            if (tag.hasText(text)) {
                tagToRemove = tag;
                break;
            }
        }
        if (tagToRemove != null) {
            taggable.removeTag(tagToRemove);
            Ebean.update(taggable);
            Ebean.delete(tagToRemove);
            return ok();
        }
        return notFound();
    }

    private static Taggable findTaggable(Long id, String clazz) {
        try {
            Field findField = Class.forName(clazz).getField("find");
            Model.Finder<Long, Taggable> find = (Model.Finder<Long, Taggable>) findField.get(clazz);
            return find.byId(id);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

}
