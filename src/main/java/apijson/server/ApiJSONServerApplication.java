package apijson.server;

import apijson.JSON;
import apijson.Log;
import apijson.StringUtil;
import apijson.framework.*;
import apijson.server.component.ApiJSONServerAPIJSONCreator;
import apijson.server.component.ApiJSONServerJSONParser;
import apijson.server.config.ApiJSONConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@SpringBootApplication
public class ApiJSONServerApplication {
    private static final Logger logger= LoggerFactory.getLogger(ApiJSONServerApplication.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext context=SpringApplication.run(ApiJSONServerApplication.class, args);
        Environment environment=context.getEnvironment();

        // 使用 fastjson
        apijson.JSON.JSON_OBJECT_CLASS = JSONObject.class;
        apijson.JSON.JSON_ARRAY_CLASS = JSONArray.class;

        Log.DEBUG = false;
        if("true".equals(environment.getProperty("apijsonServer.debug"))){
            Log.DEBUG = true;
        }

        // 配置
        ApiJSONConfig apiJSONConfig = context.getBean(ApiJSONConfig.class);


        // 加载驱动
        try{
            if(!StringUtil.isEmpty(apiJSONConfig.getDriverClassName(), true))
            Class.forName(apiJSONConfig.getDriverClassName());
        }catch (Exception e){
            logger.warn("load driver error: " + e.getMessage(), e);
        }


        APIJSONVerifier.ENABLE_VERIFY_ROLE = apiJSONConfig.isNeedVerifyRole();
        APIJSONVerifier.ENABLE_VERIFY_COLUMN = apiJSONConfig.isVerifyColumn();
        APIJSONVerifier.ENABLE_VERIFY_CONTENT = apiJSONConfig.isNeedVerifyContent();
        APIJSONFunctionParser.ENABLE_REMOTE_FUNCTION = apiJSONConfig.isRemoteFunction();


        JSON.DEFAULT_JSON_PARSER =context.getBean(ApiJSONServerJSONParser.class);
        // 配置 creator
        APIJSONCreator creator=context.getBean(ApiJSONServerAPIJSONCreator.class);

        APIJSONApplication.DEFAULT_APIJSON_CREATOR=creator;
        APIJSONSQLConfig.APIJSON_CREATOR = creator;
        APIJSONParser.APIJSON_CREATOR = creator;
        APIJSONController.APIJSON_CREATOR = creator;

        logger.info("APIJSONServerApplication started on port: " + environment.getProperty("server.port"));


    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
