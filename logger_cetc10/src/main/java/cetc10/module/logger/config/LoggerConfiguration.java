package cetc10.module.logger.config;

import cetc10.module.logger.aop.SysLogDebugAspect;
import cetc10.module.logger.common.Cetc10Logger;
import cetc10.module.logger.common.Cetc10LoggerFactory;
import cetc10.module.logger.common.LoggerConfigProperties;
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
    public void init() throws Exception {
        loggerConfigProperties.setDebugOn(true);
        new Cetc10Logger().init(loggerConfigProperties.isDebugOn(), loggerConfigProperties.getSysName(), loggerConfigProperties.getSoftwareId());
    }
}
