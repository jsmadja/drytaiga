package models;

import java.util.List;

public interface Commentable {

    void addComment(Comment comment);

    Long getId();

    List<Comment> getComments();

    Class getType();
}
