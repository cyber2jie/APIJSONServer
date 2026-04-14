package apijson.server.component;

import apijson.JSONParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ApiJSONServerJSONParser implements JSONParser<JSONObject, JSONArray> {
    private static final Feature[] DEFAULT_FASTJSON_FEATURES = {Feature.OrderedField, Feature.UseBigDecimal};
    @Override
    public JSONObject createJSONObject() {
        return new JSONObject(true);
    }

    @Override
    public JSONArray createJSONArray() {
        return new JSONArray();
    }

    @Override
    public String toJSONString(Object obj, boolean format) {
        return obj == null || obj instanceof String ? (String) obj : JSON.toJSONString(obj);
    }

    @Override
    public Object parseJSON(Object json) {
        return JSON.parse(toJSONString(json), DEFAULT_FASTJSON_FEATURES);
    }

    @Override
    public JSONObject parseObject(Object json) {
        return JSON.parseObject(toJSONString(json), DEFAULT_FASTJSON_FEATURES);
    }

    @Override
    public <T> T parseObject(Object json, Class<T> clazz) {
        return JSON.parseObject(toJSONString(json), clazz, DEFAULT_FASTJSON_FEATURES);
    }

    @Override
    public JSONArray parseArray(Object json) {
        return JSON.parseArray(toJSONString(json));
    }

    @Override
    public <T> List<T> parseArray(Object json, Class<T> clazz) {
        return JSON.parseArray(toJSONString(json), clazz);
    }

}
