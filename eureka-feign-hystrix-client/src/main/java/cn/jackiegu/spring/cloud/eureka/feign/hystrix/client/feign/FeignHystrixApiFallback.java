package cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.feign;

import cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Map<String, Object> add(UserEntity user) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        result.put("msg", "失败, 熔断");
        return result;
    }
}
