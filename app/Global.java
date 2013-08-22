import actions.GlobalAction;
import com.google.common.collect.Lists;
import play.Application;
import play.GlobalSettings;
import play.api.mvc.EssentialAction;
import play.api.mvc.EssentialFilter;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import views.TestValues;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static misc.EmailService.sendEmailWithStacktrace;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        InitialData.insert(app);
    }

    static class InitialData {
        public static void insert(Application application) {
            TestValues.createValues();
        }
    }

    @Override
    public Result onError(Http.RequestHeader requestHeader, Throwable throwable) {
        sendEmailWithStacktrace(requestHeader, throwable);
        return super.onError(requestHeader, throwable);
    }

    @Override
    public Action onRequest(Http.Request request, Method actionMethod) {
        return new GlobalAction();
    }

}