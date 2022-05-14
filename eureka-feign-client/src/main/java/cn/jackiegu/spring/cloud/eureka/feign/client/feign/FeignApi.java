package cn.jackiegu.spring.cloud.eureka.feign.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign调用API
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@FeignClient(value = "eureka-client", path = "/eureka/client")
public interface FeignApi {

    @GetMapping("/hello/{name}")
    String hello(@PathVariable("name") String name);
}
