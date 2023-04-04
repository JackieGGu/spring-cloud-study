package cn.jackiegu.spring.cloud.eureka.feign.client.controller;

import cn.jackiegu.spring.cloud.eureka.feign.client.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Feign测试Controller
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@RestController
@RequestMapping
public class FeignController {

    @Autowired
    private FeignService feignService;

    @GetMapping("hello/{name}")
    public String hello(@PathVariable(value = "name") String name) {
        return feignService.hello(name);
    }
}
