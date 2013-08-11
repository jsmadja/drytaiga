package plugins;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.common.io.Files;
import models.Document;
import models.S3File;
import play.Application;
import play.Logger;
import play.Plugin;
import play.data.Form;

import java.io.File;
import java.io.IOException;

import static play.data.Form.form;

public class StoragePlugin extends Plugin {

    private final Application application;

    public StoragePlugin(Application application) {
        this.application = application;
    }

    public static Document store(File file, String path, String contentType) {
        if (S3Plugin.isEnabled()) {
            return storeInS3(file, path);
        }
        return storeInFileSystem(file, path, contentType);
    }

    private static Document storeInFileSystem(File file, String path, String contentType) {
        try {
            Files.createParentDirs(file);
            Form<Document> form = form(Document.class).bindFromRequest();
            String name = form.data().get("name");
            String absolutePath = "/tmp/" + path;
            String url = "file://" + absolutePath;
            long contentLength = file.length();
            Files.copy(file, new File(absolutePath));
            return new Document(name, url, contentLength, contentType);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Document storeInS3(File file, String path) {
        Form<Document> form = form(Document.class).bindFromRequest();
        String name = form.data().get("name");
        S3File s3File = createS3File(file, path);
        String url = s3File.getUrl().toString();
        ObjectMetadata objectMetadata = s3File.getObjectMetadata();
        long contentLength = objectMetadata.getContentLength();
        String contentType = objectMetadata.getContentType();
        return new Document(name, url, contentLength, contentType);
    }

    private static S3File createS3File(File file, String path) {
        S3File s3File = new S3File();
        s3File.file = file;
        s3File.name = path;
        s3File.save();
        return s3File;
    }

    @Override
    public void onStart() {
        Logger.info("Using S3 Storage: " + S3Plugin.isEnabled());
        Logger.info("Using Local Storage: " + !S3Plugin.isEnabled());
    }

    @Override
    public boolean enabled() {
        return true;
    }

}
