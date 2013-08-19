package controllers;

import models.BlogPost;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Iterator;

public class Blog extends Controller {

    public static Result index() {
        Iterator<BlogPost> iterator = BlogPost.find.all().iterator();
        BlogPost blogPost = null;
        if(iterator.hasNext()) {
            blogPost = iterator.next();
        }
        return ok(views.html.blog.index.render(null, blogPost, blogPostForm()));
    }

    public static Result save() {
        Form<BlogPost> blogPostForm = blogPostForm().bindFromRequest();
        BlogPost blogPost = blogPostForm.get();
        blogPost.save();
        return ok(views.html.blog.index.render(null, blogPost, blogPostForm()));
    }

    public static Result edit(Long id) {
        Form<BlogPost> form = blogPostForm();
        return ok(views.html.blog.index.render(null, BlogPost.find.byId(id), form));
    }

    public static Result update() {
        Form<BlogPost> blogPostForm = blogPostForm().bindFromRequest();
        BlogPost blogPost = blogPostForm.get();
        blogPost.update();
        return ok(views.html.blog.index.render(null, blogPost, blogPostForm()));
    }

    private static Form<BlogPost> blogPostForm() {
        return new Form<BlogPost>(BlogPost.class);
    }

}
