package controllers;

import com.avaje.ebean.Expression;
import com.avaje.ebean.Query;
import com.google.common.base.Joiner;
import misc.ColumnValueResolver;
import misc.TableFilter;
import models.ApplianceStatus;
import models.Applicant;
import models.Comment;
import models.Document;
import play.data.Form;
import play.mvc.Result;
import views.html.applicants.index;
import views.html.applicants.read;

import static com.avaje.ebean.Expr.and;
import static com.avaje.ebean.Expr.eq;
import static controllers.routes.Applicants;
import static misc.TableFilter.createNode;
import static misc.TableFilter.url;
import static models.Applicant.find;
import static play.data.Form.form;

public class Applicants extends AjaxController {

    public static Result list() {
        return ok(index.render(user()));
    }

    public static Result all() {
        return filter("");
    }

    public static Result filter(String status) {
        return ok(createNode(request(), accountApplicants(status),
                new ColumnValueResolver<Applicant>() {
                    @Override
                    public String resolve(Applicant value) {
                        return url(Applicants.read(value.getId()), value.getFullname());
                    }
                },
                new ColumnValueResolver<Applicant>() {
                    @Override
                    public String resolve(Applicant value) {
                        return value.getApplianceStatus().name();
                    }
                }
        ));
    }

    private static Query<Applicant> accountApplicants(String applianceStatus) {
        Expression expression = eq("account", account());
        if (!applianceStatus.isEmpty()) {
            expression = and(expression, eq("applianceStatus", ApplianceStatus.valueOf(applianceStatus)));
        }
        return Applicant.find.where(expression);
    }

    public static Result read(Long id) {
        Applicant applicant = find.byId(id);
        if (user().canAccessTo(applicant)) {
            return ok(read.render(applicant, user(), documentForm(), commentForm()));
        }
        return forbidden();
    }

    public static Result changeApplianceStatus(Long id) {
        Applicant applicant = find.byId(id);
        if (user().canAccessTo(applicant)) {
            ApplianceStatus applianceStatus = ApplianceStatus.values()[Integer.valueOf(form().bindFromRequest().data().get("applianceStatus"))];
            applicant.updateStatus(applianceStatus);
            applicant.update();
            return ok();
        }
        return forbidden();
    }

    private static Form<Document> documentForm() {
        return form(Document.class);
    }

    private static Form<Comment> commentForm() {
        return form(Comment.class);
    }

    public static String getTags(Applicant applicant) {
        return Joiner.on(",").join(applicant.getTags());
    }

}
