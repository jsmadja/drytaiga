package controllers;

import com.avaje.ebean.Ebean;
import controllers.security.SecuredController;
import models.Feedback;
import models.FeedbackType;
import play.data.Form;
import play.mvc.Result;

import java.util.Map;

import static views.html.feedbacks.index.*;

public class Feedbacks extends SecuredController {

    public static Result list() {
        return ok(render(user()));
    }

    public static Result save() {
        Map<String,String> data = new Form<Feedback>(Feedback.class).bindFromRequest().data();
        FeedbackType feedbackType = FeedbackType.valueOf(data.get("feedbackType"));
        Feedback feedback = new Feedback(data.get("text"), feedbackType, user());
        Ebean.save(feedback);
        return ok();
    }

}
