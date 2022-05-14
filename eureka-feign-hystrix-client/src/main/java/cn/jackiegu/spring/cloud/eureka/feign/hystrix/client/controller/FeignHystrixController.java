package cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.controller;

import cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.entity.UserEntity;
import cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.service.FeignHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Feign测试Controller
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@RestController
@RequestMapping
public class FeignHystrixController {

    @Autowired
    private FeignHystrixService feignHystrixService;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable(value = "name") String name) {
        return feignHystrixService.hello(name);
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody UserEntity user) {
        return feignHystrixService.add(user);
    }
}
