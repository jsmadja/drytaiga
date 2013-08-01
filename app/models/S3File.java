package models;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import play.db.ebean.Model;
import plugins.S3Plugin;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Entity
public class S3File extends Model {

    @Id
    public UUID id;

    private String bucket;

    public String name;

    @Transient
    public File file;

    public URL getUrl() throws MalformedURLException {
        return new URL("https://s3.amazonaws.com/" + bucket + "/" + getActualFileName());
    }

    private String getActualFileName() {
        return id + "/" + name;
    }

    @Override
    public void save() {
        this.bucket = S3Plugin.s3Bucket;
        super.save();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, getActualFileName(), file);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        S3Plugin.amazonS3.putObject(putObjectRequest);
    }

    @Override
    public void delete() {
        S3Plugin.amazonS3.deleteObject(bucket, getActualFileName());
        super.delete();
    }

}