package controllers;

import models.Position;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import play.api.mvc.Call;
import play.data.Form;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public abstract class AjaxController extends SecuredController {

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

    protected static Result toJson(List<String> values) {
        ArrayNode json = JsonNodeFactory.instance.arrayNode();
        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
        for (String value : values) {
            jsonNodes.put("key", values.indexOf(value));
            jsonNodes.put("value", value);
        }
        json.add(jsonNodes);
        return ok(json);
    }

    protected static void addError(Form<Position> form, String fieldName, String messageKey, Object ... arguments) {
        List<ValidationError> errors = form.errors().get(fieldName);
        if (errors == null) {
            errors = new ArrayList<ValidationError>();
            form.errors().put(fieldName, errors);
        }
        errors.add(new ValidationError(fieldName, messageKey, newArrayList(arguments)));
    }

}
