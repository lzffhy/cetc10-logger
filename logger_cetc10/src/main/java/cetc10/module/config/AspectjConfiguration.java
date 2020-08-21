package cetc10.module.config;

import cetc10.module.aop.SysLogInfoAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectjConfiguration {

    @Bean
    public SysLogInfoAspect logInfoAspect() {
        return new SysLogInfoAspect();
    }
}
