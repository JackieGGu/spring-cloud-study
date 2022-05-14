package cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.feign;

import org.springframework.stereotype.Component;

/**
 * FeignHystrixApi熔断处理
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@Component
public class FeignHystrixApiFallback implements FeignHystrixApi {

    @Override
    public String hello(String name) {
        return "hello " + name + ", this is fallback";
    }
}
