package controllers;

import models.Member;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import static play.data.Form.form;

public class Application extends Controller {

    public static class LoginForm {
        public String email;
        public String password;

        public String validate() {
            if (Member.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }

    public static Result login() {
        return ok(views.html.login.render(form(LoginForm.class)));
    }

    public static Result authenticate() {
        Form<LoginForm> loginForm = form(LoginForm.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(views.html.login.render(loginForm));
        }
        String email = loginForm.get().email;
        session("email", email);
        Member member = Member.findByEmail(email);
        member.updateLastLoginDate();
        return redirect(routes.Dashboard.index());
    }

    public static Result logout() {
        session().clear();
        return redirect(routes.Application.login());
    }

}
