package misc;

import org.slf4j.MDC;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class MDCFilter extends Action.Simple {

    private final Http.Request request;

    public MDCFilter(Http.Request request) {
        this.request = request;
    }

    public Result call(Http.Context ctx) throws Throwable {
        MDC.put("user", ctx.session().get("email"));
        MDC.put("uri", request.uri());
        return delegate.call(ctx);
    }

}
