package controllers;

import models.User;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import play.api.mvc.Call;
import play.data.Form;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.Map;

import static models.User.currentUser;

public abstract class AjaxController extends Controller {

    protected static User user() {
        return currentUser(request());
    }

    protected static Status badRequest(Form<?> form) {
        return badRequest(toJson(form));
    }

    protected static Result ok(Call call) {
        return ok(call.url());
    }

    private static ArrayNode toJson(Form<?> form) {
        ArrayNode json = JsonNodeFactory.instance.arrayNode();
        Map<String, List<ValidationError>> errors = form.errors();
        for (Map.Entry<String, List<ValidationError>> validationError : errors.entrySet()) {
            for (ValidationError error : validationError.getValue()) {
                ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
                jsonNodes.put("field", validationError.getKey());
                jsonNodes.put("error", Messages.get(error.message(), error.arguments()));
                json.add(jsonNodes);
            }
        }
        return json;
    }

}
