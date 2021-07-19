package cn.jackiegu.spring.cloud.consumer.support;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Bean 实例支持类
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@Component
public class BeansSupport {

    /**
     * 在使用Ribbon负载均衡时必须使用@LoadBalanced注解
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        // 设置超时时间
        // SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // factory.setConnectTimeout(2000);
        // factory.setReadTimeout(3000);
        // return new RestTemplate(factory);

        return new RestTemplate();
    }
}
