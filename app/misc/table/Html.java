package misc.table;

import controllers.Accounts;
import play.api.mvc.Call;

import static java.text.MessageFormat.format;
import static misc.Math.percent;

public class Html {

    public static String url(Call href, String value) {
        return format("<a href=''{0}''>{1}</a>", href.url(), value);
    }

    public static String labelOrProgressbar(Integer up, Integer down) {
        int percent = percent(up, down);
        if (percent == 100) {
            return h6(span("label label-danger", "MAX"));
        }
        if (down == Integer.MAX_VALUE) {
            return h6(span("label label-success", "UNLIMITED"));
        }
        return progressbar(up, down);
    }


    public static String progressbar(Integer up, Integer down) {
        int percent = percent(up, down);
        return format("<div class=''progress'' style=''height: 10px; margin-bottom: 0px; width: 100px;'' title=''{0}/{1}''><div class=''progress-bar'' role=''progressbar'' aria-valuenow=''{2}'' aria-valuemin=''0'' aria-valuemax=''100'' style=''width: {3}%;''></div></div>", up, down, percent, percent);
    }

    public static String span(String className, String value) {
        return format("<span class=''{0}''>{1}</span>", className, value);
    }

    public static String h6(String s) {
        return "<h6>" + s + "</h6>";
    }

}
