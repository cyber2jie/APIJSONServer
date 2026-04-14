package apijson.server.internal;

import apijson.RequestMethod;
import apijson.framework.APIJSONFunctionParser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;

public class ApiJSONServerFunctionParser extends APIJSONFunctionParser<Long, JSONObject, JSONArray> {
	public ApiJSONServerFunctionParser() {
		this(null);
	}
	public ApiJSONServerFunctionParser(HttpSession session) {
		this(null, null, 0, null, session);
	}
	public ApiJSONServerFunctionParser(RequestMethod method, String tag, int version, JSONObject curObj, HttpSession session) {
		super(method, tag, version, curObj, session);
	}

}