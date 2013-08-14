package models;

import java.util.List;

public interface Documentable {

    void addDocument(Document document);

    Long getId();

    List<Document> getDocuments();
}
