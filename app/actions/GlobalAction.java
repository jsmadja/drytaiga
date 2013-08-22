package actions;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.MDC;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class GlobalAction extends Action.Simple {

    private StopWatch stopWatch;

    @Override
    public Result call(Http.Context context) throws Throwable {
        startWatch();
        mdc(context);
        Result call = delegate.call(context);
        stopWatch();
        Logger.debug(stopWatch.getTime() + "ms");
        return call;
    }

    private void stopWatch() {
        stopWatch.stop();
    }

    private void startWatch() {
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    private void mdc(Http.Context context) {
        MDC.put("user", context.session().get("email"));
        MDC.put("uri", context.request().uri().split("\\?")[0]);
    }

}
