package controllers;

import models.Company;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.sendEmail());
    }

    public static boolean isMemberOf(Company company) {
        return company.hasMember(Context.current().request().username());
    }

}