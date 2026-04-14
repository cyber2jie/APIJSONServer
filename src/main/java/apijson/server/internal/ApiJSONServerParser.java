package apijson.server.internal;

import apijson.RequestMethod;
import apijson.framework.APIJSONObjectParser;
import apijson.framework.APIJSONParser;
import apijson.orm.SQLConfig;
import apijson.server.config.ApiJSONConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;


public class ApiJSONServerParser extends APIJSONParser<Long, JSONObject, JSONArray> {

    public static final Map<String, HttpSession> KEY_MAP;
    static {
      KEY_MAP = new HashMap<>();
    }

    private ApiJSONConfig apiJSONConfig;

    public ApiJSONServerParser() {
        super();
    }
    public ApiJSONServerParser(ApiJSONConfig apiJSONConfig) {
        super();
        this.apiJSONConfig = apiJSONConfig;
    }
    public ApiJSONServerParser(RequestMethod method) {
        super(method);
    }
    public ApiJSONServerParser(RequestMethod method, boolean needVerify) {
        super(method, needVerify);
    }

    @Override
    public int getMaxQueryCount() {
        if(apiJSONConfig!=null)
        return apiJSONConfig.getMaxQueryCount();
        return super.getMaxQueryCount();
    }

    @Override
    public APIJSONObjectParser<Long, JSONObject, JSONArray> createObjectParser(JSONObject request, String parentPath
            , SQLConfig<Long, JSONObject, JSONArray> arrayConfig
            , boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {
        return new ApiJSONServerObjectParser(getSession(), request, parentPath, arrayConfig
                , isSubquery, isTable, isArrayMainTable).setMethod(getMethod()).setParser(this);
    }

}
