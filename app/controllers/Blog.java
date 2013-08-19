package controllers;

import models.BlogPost;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Blog extends Controller {

    public static Result index() {
        BlogPost blogPost = new BlogPost("<b>Content</b>");
        return ok(views.html.blog.index.render(null, blogPost, blogPostForm()));
    }

    public static Result save() {
        Form<BlogPost> blogPostForm = blogPostForm().bindFromRequest();
        BlogPost blogPost = blogPostForm.get();
        blogPost.save();
        return ok(views.html.blog.index.render(null, blogPost, blogPostForm()));
    }

    private static Form<BlogPost> blogPostForm() {
        return new Form<BlogPost>(BlogPost.class);
    }

}
