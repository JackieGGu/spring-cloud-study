package cn.jackiegu.spring.cloud.eureka.ribbon.client.controller;

import cn.jackiegu.spring.cloud.eureka.ribbon.client.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ribbon测试Controller
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@RestController
@RequestMapping
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;

    @GetMapping("hello/{name}")
    public String hello(@PathVariable(value = "name") String name) {
        return ribbonService.hello(name);
    }
}
