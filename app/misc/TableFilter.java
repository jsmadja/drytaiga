package misc;

import com.avaje.ebean.Page;
import com.avaje.ebean.Query;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.api.mvc.Call;
import play.libs.Json;
import play.mvc.Http;

import java.util.List;
import java.util.Map;

public class TableFilter<T> {

    public static <T> ObjectNode createNode(Http.Request request, Query<T> query, ColumnValueResolver ... columnValuesResolvers) {
        Map<String, String[]> params = request.queryString();
        Integer iTotalRecords = query.findRowCount();
        String filter = params.get("sSearch")[0];
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);
        Integer startPage = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;

        Page<T> page = query.
                findPagingList(pageSize).
                setFetchAhead(false).
                getPage(startPage);

        Integer totalDisplayRecords = page.getTotalRowCount();

        ObjectNode result = Json.newObject();
        result.put("sEcho", Integer.valueOf(params.get("sEcho")[0]));
        result.put("iTotalRecords", iTotalRecords);
        result.put("iTotalDisplayRecords", totalDisplayRecords);

        ArrayNode array = result.putArray("aaData");
        for (T rowValue : page.getList()) {
            ObjectNode row = Json.newObject();
            for (int i = 0; i < columnValuesResolvers.length; i++) {
                row.put(Integer.toString(i), columnValuesResolvers[i].resolve(rowValue));
            }
            array.add(row);
        }
        return result;
    }

    public static String url(String url, String value) {
        return "<a href='"+url+"'>" + value + "</a>";
    }

    public static String url(Call call, String value) {
        return url(call.url(), value);
    }
}
