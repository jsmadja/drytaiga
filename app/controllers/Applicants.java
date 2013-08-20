package controllers;

import com.avaje.ebean.Expression;
import com.avaje.ebean.Query;
import com.google.common.base.Joiner;
import misc.table.ColumnValueResolver;
import misc.table.TableFilter;
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
import static misc.table.TableFilter.createNode;
import static play.data.Form.form;

public class Applicants extends AjaxController {

    private static ColumnValueResolver<Applicant> fullnameResolver = new ColumnValueResolver<Applicant>() {
        @Override
        public String resolve(Applicant applicant) {
            return TableFilter.url(Applicants.read(applicant), applicant.getFullname());
        }
    };
    private static ColumnValueResolver<Applicant> applianceStatusResolver = new ColumnValueResolver<Applicant>() {
        @Override
        public String resolve(Applicant applicant) {
            return applicant.getApplianceStatus().name();
        }
    };;

    public static Result list() {
        return ok(index.render(user()));
    }

    public static Result all() {
        return filter(null);
    }

    public static Result filter(String s) {
        ApplianceStatus status = s == null ? null : ApplianceStatus.valueOf(s);
        return ok(createNode(request(), accountApplicants(status), fullnameResolver, applianceStatusResolver));
    }

    private static Query<Applicant> accountApplicants(ApplianceStatus applianceStatus) {
        Expression expression = eq("account", account());
        if (applianceStatus != null) {
            expression = and(expression, eq("applianceStatus", applianceStatus));
        }
        return Applicant.find.where(expression);
    }

    public static Result read(Applicant applicant) {
        if (user().canAccessTo(applicant)) {
            return ok(read.render(applicant, user(), documentForm(), commentForm()));
        }
        return forbidden();
    }

    public static Result changeApplianceStatus(Applicant applicant) {
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
