package misc.table;

import play.mvc.Http;

import java.util.Map;

public class TableFilterConfiguration {

    public int sEcho;
    public int iSortCol;
    public String sSearch;
    public String iSortDir;
    public int iDisplayStart;
    public int iDisplayLength;

    public static TableFilterConfiguration readFrom(Http.Request request) {
        Map<String, String[]> params = request.queryString();
        TableFilterConfiguration configuration = new TableFilterConfiguration();
        configuration.sEcho = Integer.valueOf(params.get("sEcho")[0]);
        configuration.sSearch = params.get("sSearch")[0];
        configuration.iDisplayLength = Integer.valueOf(params.get("iDisplayLength")[0]);
        configuration.iSortCol = Integer.valueOf(params.get("iSortCol_0")[0]);
        configuration.iDisplayStart = Integer.valueOf(params.get("iDisplayStart")[0]);
        configuration.iSortDir = params.get("sSortDir_0")[0];
        return configuration;
    }

}
