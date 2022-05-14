package cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.feign;

import cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Feign调用API
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@Primary
@FeignClient(value = "eureka-client", path = "/eureka/client", fallback = FeignHystrixApiFallback.class)
public interface FeignHystrixApi {

    @GetMapping("/hello/{name}")
    String hello(@PathVariable("name") String name);

    @PostMapping("/add")
    Map<String, Object> add(@RequestBody UserEntity user);
}
