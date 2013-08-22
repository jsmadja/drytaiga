package misc.table;

import com.avaje.ebean.Page;
import com.avaje.ebean.Query;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.api.mvc.Call;
import play.libs.Json;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class TableFilter {

    public static <T> ObjectNode createNode(Http.Request request, Query<T> query, final ColumnValueResolver... columnValuesResolvers) {
        TableFilterConfiguration configuration = TableFilterConfiguration.readFrom(request);
        Collection<T> records = getRecords(query, configuration, columnValuesResolvers);
        ObjectNode json = Json.newObject();
        json.put("sEcho", configuration.sEcho);
        json.put("iTotalRecords", query.findRowCount());
        json.put("iTotalDisplayRecords", records.size());
        ArrayNode rows = json.putArray("aaData");
        for (T record : records) {
            ObjectNode row = Json.newObject();
            for (int i = 0; i < columnValuesResolvers.length; i++) {
                row.put(Integer.toString(i), columnValuesResolvers[i].label(record));
            }
            rows.add(row);
        }
        return json;
    }

    private static <T> Collection<T> getRecords(Query<T> query, TableFilterConfiguration configuration, ColumnValueResolver... columnValuesResolvers) {
        List<T> records = find(query, configuration);
        records = filter(columnValuesResolvers, configuration.sSearch, records);
        sort(columnValuesResolvers[configuration.iSortCol], configuration.iSortDir, records);
        return records;
    }

    private static <T> List<T> find(Query<T> query, TableFilterConfiguration configuration) {
        Integer pageSize = configuration.iDisplayLength;
        Integer startPage = configuration.iDisplayStart / pageSize;
        Page<T> page = query.
                findPagingList(pageSize).
                setFetchAhead(false).
                getPage(startPage);
        return page.getList();
    }

    private static <T> List<T> filter(ColumnValueResolver[] columnValuesResolvers, String filter, List<T> records) {
        if (isNotBlank(filter)) {
            records = newArrayList(Collections2.filter(records, withPredicate(columnValuesResolvers, filter)));
        }
        return records;
    }

    private static <T> void sort(final ColumnValueResolver sortBy, final String order, List<T> records) {
        Collections.sort(records, new Comparator<T>() {
            @Override
            public int compare(T t1, T t2) {
                if ("asc".equalsIgnoreCase(order)) {
                    return sortBy.value(t1).compareTo(sortBy.value(t2));
                }
                return sortBy.value(t2).compareTo(sortBy.value(t1));
            }
        });
    }

    private static <T> Predicate<T> withPredicate(final ColumnValueResolver[] columnValuesResolvers, final String filter) {
        return new Predicate<T>() {
            @Override
            public boolean apply(@Nullable T filtrable) {
                for (ColumnValueResolver columnValuesResolver : columnValuesResolvers) {
                    if (columnValuesResolver.label(filtrable).contains(filter)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

}
