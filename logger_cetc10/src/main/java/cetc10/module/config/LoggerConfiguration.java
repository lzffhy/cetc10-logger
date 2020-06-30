package cetc10.module.config;

import cetc10.module.common.Cetc10Logger;
import cetc10.module.common.LoggerConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
@EnableConfigurationProperties(LoggerConfigProperties.class)
@ConditionalOnProperty(prefix = "cetc10.logger", name = "isDebugOn", havingValue = "true")
public class LoggerConfiguration {

    @Autowired
    private LoggerConfigProperties loggerConfigProperties;

    @Bean
    public Object init() throws Exception {
        loggerConfigProperties.setDebugOn(true);
        new Cetc10Logger().init(loggerConfigProperties.isDebugOn(), loggerConfigProperties.getSysName(), loggerConfigProperties.getSoftwareId());
        return new Object();
    }
}
