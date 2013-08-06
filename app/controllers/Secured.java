package controllers;

import models.Company;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context context) {
        return context.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context context) {
        return redirect(routes.Application.login());
    }

    public static boolean isMemberOf(Company company) {
        return Company.isMember(
                company,
                Context.current().request().username()
        );
    }

    public static boolean isMemberOf(Long company) {
        return Company.isMember(
                company,
                Context.current().request().username()
        );
    }

}