package controllers;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import models.Applicant;
import models.BaseModel;
import models.Opening;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import play.api.mvc.Call;
import play.mvc.Result;

import java.util.List;

import static com.google.common.collect.Collections2.filter;

public class Search extends AjaxController {

    public static Result search() {
        ArrayNode json = JsonNodeFactory.instance.arrayNode();
        String term = request().getQueryString("term").toLowerCase();
        searchInApplicants(json, term);
        searchInOpenings(json, term);
        return ok(json);
    }

    private static void searchInApplicants(ArrayNode json, final String term) {
        List<Applicant> filteredApplicants = Lists.newArrayList(filter(account().getApplicants(), new Predicate<Applicant>() {
            public boolean apply(Applicant applicant) {
                return applicant.getFullname().toLowerCase().contains(term);
            }
        }));
        filteredApplicants = filteredApplicants.subList(0, Math.min(5, filteredApplicants.size()));
        for (Applicant applicant : filteredApplicants) {
            json.add(createNode("Applicants", applicant, applicant.getFullname(), routes.Applicants.read(applicant.getId())));
        }
    }

    private static void searchInOpenings(ArrayNode json, final String term) {
        List<Opening> filteredOpenings = Lists.newArrayList(filter(account().getOpenings(), new Predicate<Opening>() {
            public boolean apply(Opening opening) {
                return opening.getName().toLowerCase().contains(term);
            }
        }));
        filteredOpenings = filteredOpenings.subList(0, Math.min(5, filteredOpenings.size()));
        for (Opening opening : filteredOpenings) {
            json.add(createNode("Openings", opening, opening.getName(), routes.Openings.read(opening.getId())));
        }
    }

    private static ObjectNode createNode(String category, BaseModel model, String label, Call call) {
        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
        jsonNodes.put("id", model.getId());
        jsonNodes.put("label", label);
        jsonNodes.put("value", label);
        jsonNodes.put("category", category);
        jsonNodes.put("url", call.url());
        return jsonNodes;
    }

}
