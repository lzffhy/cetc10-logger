package cetc10.module.logger.config;

import cetc10.module.logger.aop.SysLogDebugAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectjConfiguration {

    @Bean
    public SysLogDebugAspect logDebugAspect() {
        return new SysLogDebugAspect();
    }
}
