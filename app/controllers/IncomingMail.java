package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import plugins.ApplicantProducerPlugin;

public class IncomingMail extends Controller {

    public static Result incomingMail() {
        Http.MultipartFormData multipartFormData = request().body().asMultipartFormData();
        ApplicantProducerPlugin.createApplicant(multipartFormData);
        return ok();
    }

}
