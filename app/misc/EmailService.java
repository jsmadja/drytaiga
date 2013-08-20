package misc;

import models.Mail;
import play.mvc.Http;

import static java.lang.String.format;

public class EmailService {

    public static void sendEmailWithStacktrace(Http.RequestHeader requestHeader, Throwable throwable) {
        StringBuilder body = new StringBuilder();
        body.append(format("user: %s", email())).append("<br/>");
        body.append(format("%s %s", requestHeader.method(), requestHeader.uri())).append("<br/>");
        body.append(new PrettyStacktrace(throwable).toString());
        Mail mail = new Mail("julien.smadja@gmail.com", "Une erreur est survenue", body.toString(), body.toString());
        mail.send();
    }

    private static String email() {
        return Http.Context.current().session().get("email");
    }

}
