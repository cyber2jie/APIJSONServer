package apijson.server.internal;

import apijson.StringUtil;
import apijson.framework.APIJSONSQLConfig;
import apijson.orm.SQLConfig;
import apijson.server.ApiJSONServerRuntimeException;
import apijson.server.config.ApiJSONConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;


public class ApiJSONServerSQLConfig extends APIJSONSQLConfig<Long, JSONObject, JSONArray> {

    private ApiJSONConfig apiJSONConfig;

    public ApiJSONServerSQLConfig(ApiJSONConfig apiJSONConfig) {
        super();
        this.apiJSONConfig = apiJSONConfig;
    }

    @Override
    public String gainSQLDatabase() {
        if(StringUtil.isEmpty(apiJSONConfig.getSqlDatabase(),true)){
            return SQLConfig.DATABASE_MYSQL;
        }
       return apiJSONConfig.getSqlDatabase();
    }

    @Override
    public String gainSQLSchema() {
        String schema= apiJSONConfig.getSqlSchema();
        if(StringUtil.isEmpty(schema,true)){
            throw new ApiJSONServerRuntimeException("sqlSchema can not be empty");
        }
        return schema;
    }

    @Override
	public String gainDBVersion() {
        if(!StringUtil.isEmpty(apiJSONConfig.getDbVersion(),true)){
            return apiJSONConfig.getDbVersion();
        }
		return super.gainDBVersion();
	}

	@JSONField(serialize = false)  // 不在日志打印 账号/密码 等敏感信息
	@Override
	public String gainDBUri() {
		return apiJSONConfig.getDbUrl();
	}

	@JSONField(serialize = false)  // 不在日志打印 账号/密码 等敏感信息
	@Override
	public String gainDBAccount() {
		return apiJSONConfig.getDbAccount();
	}

	@JSONField(serialize = false)  // 不在日志打印 账号/密码 等敏感信息
	@Override
	public String gainDBPassword() {
		return apiJSONConfig.getDbPassword();
	}

}
