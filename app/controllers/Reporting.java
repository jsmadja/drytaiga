package controllers;

import com.google.common.base.Predicate;
import controllers.security.SecuredController;
import models.Account;
import models.ApplianceStatus;
import models.Applicant;
import play.mvc.Result;
import views.html.reporting.index;

import javax.annotation.Nullable;

import static com.google.common.collect.Collections2.filter;

public class Reporting extends SecuredController {

    public static Result index() {
        return ok(index.render(user()));
    }

    public static double getTransformationRate() {
        Account account = user().getAccount();
        return Math.round((double) getHiredApplicantCount() / getApplicantCount(account));
    }

    private static int getApplicantCount(Account account) {
        return account.getApplicants().size();
    }

    private static int getHiredApplicantCount() {
        return filter(user().getAccount().getApplicants(), new Predicate<Applicant>() {
            @Override
            public boolean apply(@Nullable Applicant applicant) {
                return applicant.getApplianceStatus() == ApplianceStatus.Hired;
            }
        }).size();
    }

}
