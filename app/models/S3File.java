package models;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import plugins.S3Plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static plugins.S3Plugin.bucket;

public class S3File {

    private final String name;
    private final File file;

    public S3File(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public URL getUrl() {
        try {
            return new URL("https://s3.amazonaws.com/" + bucket + "/" + name);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void save() {
        if(S3Plugin.isEnabled()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, name, file);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            S3Plugin.amazonS3.putObject(putObjectRequest);
        }
    }

    public void delete() {
        S3Plugin.amazonS3.deleteObject(bucket, name);
    }

    public ObjectMetadata getObjectMetadata() {
        return S3Plugin.amazonS3.getObject(bucket, name).getObjectMetadata();
    }
}