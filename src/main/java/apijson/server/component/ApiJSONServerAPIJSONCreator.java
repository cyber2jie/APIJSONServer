package apijson.server.component;

import apijson.framework.APIJSONCreator;
import apijson.framework.APIJSONFunctionParser;
import apijson.framework.APIJSONSQLExecutor;
import apijson.server.config.ApiJSONConfig;
import apijson.server.internal.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ApiJSONServerAPIJSONCreator extends APIJSONCreator<Long, JSONObject, JSONArray>{
    private ApiJSONConfig apiJSONConfig;

    public ApiJSONServerAPIJSONCreator(ApiJSONConfig apiJSONConfig) {
        this.apiJSONConfig = apiJSONConfig;
    }

    @Override
    public ApiJSONServerParser createParser() {
        return new ApiJSONServerParser(apiJSONConfig);
    }

    @Override
    public APIJSONFunctionParser createFunctionParser() {
        return new ApiJSONServerFunctionParser();
    }

    @Override
    public ApiJSONServerVerifier createVerifier() {
        return new ApiJSONServerVerifier();
    }

    @Override
    public ApiJSONServerSQLConfig createSQLConfig() {
        return new ApiJSONServerSQLConfig(apiJSONConfig);
    }

    @Override
    public APIJSONSQLExecutor createSQLExecutor() {
        return new ApiJSONServerSQLExecutor();
    }

}
