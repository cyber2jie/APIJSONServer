package apijson.server.internal;

import apijson.JSON;
import apijson.NotNull;
import apijson.framework.APIJSONSQLExecutor;
import apijson.orm.SQLConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

public class ApiJSONServerSQLExecutor  extends APIJSONSQLExecutor<Long, JSONObject, JSONArray> {

    @Override
    public PreparedStatement setArgument(@NotNull SQLConfig<Long, JSONObject, JSONArray> config, @NotNull PreparedStatement statement, int index, Object value) throws SQLException {
        if (config.isPostgreSQL() && JSON.isBoolOrNumOrStr(value) == false) {
            PGobject o = new PGobject();
            o.setType("jsonb");
            o.setValue(value == null ? null : value.toString());
            statement.setObject(index + 1, o); //PostgreSQL 除了基本类型，其它的必须通过 PGobject 设置进去，否则 jsonb = varchar 等报错
            return statement;
        }

        return super.setArgument(config, statement, index, value);
    }


    @Override
    protected Object getValue(
            SQLConfig<Long, JSONObject, JSONArray> config, ResultSet rs, ResultSetMetaData rsmd, int row
            , JSONObject table, int columnIndex, String label, Map<String, JSONObject> childMap, Map<String, String> keyMap
    ) throws Exception {

        Object value = super.getValue(config, rs, rsmd, row, table, columnIndex, label, childMap, keyMap);

        return value instanceof PGobject ? JSON.parseJSON(((PGobject) value).getValue()) : value;
    }

    // 支持 !key 反选字段 和 字段名映射，依赖插件 https://github.com/APIJSON/apijson-column
    @Override
    protected String getKey(
            SQLConfig<Long, JSONObject, JSONArray> config, ResultSet rs, ResultSetMetaData rsmd, int row
            , JSONObject table, int columnIndex, Map<String, JSONObject> childMap, Map<String, String> keyMap
    ) throws Exception {

        String key = super.getKey(config, rs, rsmd, row, table, columnIndex, childMap, keyMap);
//		if (APIJSONSQLConfig.ENABLE_COLUMN_CONFIG) {
//			return ColumnUtil.compatOutputKey(key, config.getTable(), config.getMethod());
//		}

        return key;
    }

}
