package models;

import java.util.List;

public interface Taggable {

    void addTag(Tag tag);

    List<Tag> getTags();

    void removeTag(Tag tag);
}
