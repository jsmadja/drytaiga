package controllers;

import controllers.security.UnsecuredController;
import models.BlogPost;
import play.data.Form;
import play.mvc.Result;

import java.util.Iterator;
import java.util.List;

public class Blog extends UnsecuredController {

    public static BlogPost previousOf(BlogPost blogPost) {
        List<BlogPost> blogPosts = getBlogPosts();
        for (int i = 0; i < blogPosts.size(); i++) {
            BlogPost post = blogPosts.get(blogPosts.size() - i - 1);
            if (post.getId() < blogPost.getId()) {
                return post;
            }
        }
        throw new IllegalStateException("You should not invoke this method");
    }

    public static BlogPost nextOf(BlogPost blogPost) {
        for (BlogPost post : getBlogPosts()) {
            if (post.getId() > blogPost.getId()) {
                return post;
            }
        }
        throw new IllegalStateException("You should not invoke this method");
    }

    public static boolean hasPrevious(BlogPost blogPost) {
        List<BlogPost> posts = getBlogPosts();
        if (posts.isEmpty() || posts.size() == 1) {
            return false;
        }
        for (BlogPost post : posts) {
            if (post.getId() < blogPost.getId()) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNext(BlogPost blogPost) {
        List<BlogPost> posts = getBlogPosts();
        if (posts.isEmpty() || posts.size() == 1) {
            return false;
        }
        for (BlogPost post : posts) {
            if (post.getId() > blogPost.getId()) {
                return true;
            }
        }
        return false;
    }

    private static List<BlogPost> getBlogPosts() {
        return BlogPost.find.all();
    }

    public static Result read(BlogPost blogPost) {
        return ok(views.html.blog.index.render(user(), blogPost, blogPostForm()));
    }

    public static Result index() {
        Iterator<BlogPost> iterator = BlogPost.find.all().iterator();
        BlogPost blogPost = null;
        if (iterator.hasNext()) {
            blogPost = iterator.next();
        }
        return ok(views.html.blog.index.render(user(), blogPost, blogPostForm()));
    }

    public static Result save() {
        Form<BlogPost> blogPostForm = blogPostForm().bindFromRequest();
        BlogPost blogPost = blogPostForm.get();
        blogPost.save();
        return ok(views.html.blog.index.render(user(), blogPost, blogPostForm()));
    }

    public static Result edit(BlogPost blogPost) {
        return ok(views.html.blog.index.render(user(), blogPost, blogPostForm()));
    }

    public static Result update() {
        Form<BlogPost> blogPostForm = blogPostForm().bindFromRequest();
        BlogPost blogPost = blogPostForm.get();
        blogPost.update();
        return ok(views.html.blog.index.render(user(), blogPost, blogPostForm()));
    }

    private static Form<BlogPost> blogPostForm() {
        return new Form<BlogPost>(BlogPost.class);
    }

}
