package misc;

import com.avaje.ebean.Page;
import com.avaje.ebean.Query;
import com.google.common.base.Predicate;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.api.mvc.Call;
import play.libs.Json;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

import static com.google.common.collect.Collections2.filter;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class TableFilter {

    public static <T> ObjectNode createNode(Http.Request request, Query<T> query, final ColumnValueResolver... columnValuesResolvers) {
        Map<String, String[]> params = request.queryString();
        Collection<T> records = getRecords(query, params, columnValuesResolvers);
        ObjectNode result = Json.newObject();
        result.put("sEcho", Integer.valueOf(params.get("sEcho")[0]));
        result.put("iTotalRecords", (Integer) query.findRowCount());
        result.put("iTotalDisplayRecords", (Integer) records.size());
        ArrayNode array = result.putArray("aaData");
        for (T rowValue : records) {
            ObjectNode row = Json.newObject();
            for (int i = 0; i < columnValuesResolvers.length; i++) {
                row.put(Integer.toString(i), columnValuesResolvers[i].resolve(rowValue));
            }
            array.add(row);
        }
        return result;
    }

    private static <T> Collection<T> getRecords(Query<T> query, Map<String, String[]> params, ColumnValueResolver[] columnValuesResolvers) {
        String filter = params.get("sSearch")[0];
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);
        Integer startPage = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;
        Page<T> page = query.
                findPagingList(pageSize).
                setFetchAhead(false).
                getPage(startPage);

        Collection<T> records = page.getList();
        if (isNotBlank(filter)) {
            records = filter(records, withPredicate(columnValuesResolvers, filter));
        }
        return records;
    }

    private static <T> Predicate<T> withPredicate(final ColumnValueResolver[] columnValuesResolvers, final String filter) {
        return new Predicate<T>() {
            @Override
            public boolean apply(@Nullable T filtrable) {
                for (ColumnValueResolver columnValuesResolver : columnValuesResolvers) {
                    if (columnValuesResolver.resolve(filtrable).contains(filter)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public static String url(String url, String value) {
        return "<a href='" + url + "'>" + value + "</a>";
    }

    public static String url(Call call, String value) {
        return url(call.url(), value);
    }
}
