package cn.jackiegu.spring.cloud.zuul.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Spring Bean 实例支持类
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@Component
public class BeansSupport {

    public static final String ZUUL_PROPERTIES_BEAN = "ZUUL_PROPERTIES_BEAN";

    @Bean(ZUUL_PROPERTIES_BEAN)
    @Primary
    @RefreshScope
    @ConfigurationProperties("zuul")
    public ZuulProperties zuulProperties() {
        return new ZuulProperties();
    }
}
