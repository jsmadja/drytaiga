package models;

import play.mvc.Http;

import java.util.List;

public interface Documentable {

    void addDocument(Document document);

    Long getId();

    List<Document> getDocuments();

    String getFilePath(Http.MultipartFormData.FilePart file);
}
