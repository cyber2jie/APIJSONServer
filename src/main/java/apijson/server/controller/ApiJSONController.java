package apijson.server.controller;

import java.net.URLDecoder;
import java.util.Map;

import apijson.framework.APIJSONParser;
import apijson.server.config.ApiJSONConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import apijson.RequestMethod;
import apijson.StringUtil;
import apijson.framework.APIJSONController;

@RestController
@RequestMapping("")
public class ApiJSONController extends APIJSONController<Long, JSONObject, JSONArray> {

    private ApiJSONConfig apiJSONConfig;

    public ApiJSONController(ApiJSONConfig apiJSONConfig) {
        this.apiJSONConfig = apiJSONConfig;
    }

    @Override
	public APIJSONParser<Long, JSONObject, JSONArray> newParser(HttpSession session, RequestMethod method) {
		return super.newParser(session, method)
                .setNeedVerify(apiJSONConfig.isNeedVerify())
                .setNeedVerifyContent(apiJSONConfig.isNeedVerifyContent())
                .setNeedVerifyLogin(apiJSONConfig.isNeedVerifyLogin())
                .setNeedVerifyRole(apiJSONConfig.isNeedVerifyRole())
                ;
	}

	/**增删改查统一接口，这个一个接口可替代 7 个万能通用接口，牺牲一些路由解析性能来提升一点开发效率
	 * @param method
	 * @param request
	 * @param session
	 * @return
	 */
	@PostMapping(value = "{method}")  // 如果和其它的接口 URL 冲突，可以加前缀，例如改为 crud/{method} 或 Controller 注解 @RequestMapping("crud")
	@Override
	public String crud(@PathVariable("method") String method, @RequestBody String request, HttpSession session) {
		return super.crud(method, request, session);
	}

	/**全能增删改查接口，可同时进行 增、删、改、查 多种操作，
	 * 通过 @method: "POST", @gets: { "Privacy":"Privacy-CIRCLE", "User": { "@role":"LOGIN", "tag":"User" } } 等关键词指定
	 * @param request
	 * @param session
	 * @return
	 */
	@PostMapping(value = "crud")  // 直接 {method} 或 apijson/{method} 会和内置网页的路由有冲突
	// @Override
	public String crudAll(@RequestBody String request, HttpSession session) {
		return newParser(session, RequestMethod.CRUD).parse(request);
	}

	/**增删改查统一接口，这个一个接口可替代 7 个万能通用接口，牺牲一些路由解析性能来提升一点开发效率
	 * @param method
	 * @param tag
	 * @param params
	 * @param request
	 * @param session
	 * @return
	 */
	@PostMapping("{method}/{tag}")  // 如果和其它的接口 URL 冲突，可以加前缀，例如改为 crud/{method}/{tag} 或 Controller 注解 @RequestMapping("crud")
	@Override
	public String crudByTag(@PathVariable("method") String method, @PathVariable("tag") String tag, @RequestParam Map<String, String> params, @RequestBody String request, HttpSession session) {
		return super.crudByTag(method, tag, params, request, session);
	}

	/**获取
	 * 只为兼容HTTP GET请求，推荐用HTTP POST，可删除
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#GET}
	 */
	@GetMapping("get/{request}")
	public String openGet(@PathVariable("request") String request, HttpSession session) {
		try {
			request = URLDecoder.decode(request, StringUtil.UTF_8);
		} catch (Exception e) {
			// Parser 会报错
		}
		return get(request, session);
	}

}