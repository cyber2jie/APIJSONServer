package apijson.server.internal;

import apijson.NotNull;
import apijson.RequestMethod;
import apijson.framework.APIJSONObjectParser;
import apijson.orm.Join;
import apijson.orm.SQLConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ApiJSONServerObjectParser extends APIJSONObjectParser<Long, JSONObject, JSONArray> {

    public ApiJSONServerObjectParser(HttpSession session, @NotNull JSONObject request, String parentPath
            , SQLConfig<Long, JSONObject, JSONArray> arrayConfig, boolean isSubquery, boolean isTable
            , boolean isArrayMainTable) throws Exception {
        super(session, request, parentPath, arrayConfig, isSubquery, isTable, isArrayMainTable);
    }

    @Override
    public SQLConfig<Long, JSONObject, JSONArray> newSQLConfig(RequestMethod method, String table, String alias
            , JSONObject request, List<Join<Long, JSONObject, JSONArray>> joinList, boolean isProcedure) throws Exception {
        return ApiJSONServerSQLConfig.newSQLConfig(method, table, alias, request, joinList, isProcedure);
    }

}
