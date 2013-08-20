package formatters;

import play.mvc.Http;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public static String format_DD_MM_YYYY(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy", Http.Context.current().lang().toLocale()).format(date);
    }

}
