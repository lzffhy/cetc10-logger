package cetc10.module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cetc10.module.aop.SysLogDebugAspect;

@Configuration
public class AspectjConfiguration {

    @Bean
    public SysLogDebugAspect logDebugAspect() {
        return new SysLogDebugAspect();
    }
}
