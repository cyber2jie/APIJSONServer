package apijson.server.config;


import apijson.orm.SQLConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "apijson-server")
@Getter
@Setter
public class ApiJSONConfig {
    private boolean needVerify = false;
    private boolean needVerifyContent= false;
    private boolean needVerifyLogin= false;
    private boolean needVerifyRole = false;
    private boolean verifyColumn =false;
    private boolean remoteFunction = false;
    private int maxQueryCount = 200;


    //sqlConfig
    private String driverClassName;
    private String sqlDatabase= SQLConfig.DATABASE_MYSQL;
    private String sqlSchema;
    private String dbVersion;
    private String dbUrl;
    private String dbAccount;
    private String dbPassword;


}
