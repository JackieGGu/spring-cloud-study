package cn.jackiegu.spring.cloud.consumer.support;

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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
