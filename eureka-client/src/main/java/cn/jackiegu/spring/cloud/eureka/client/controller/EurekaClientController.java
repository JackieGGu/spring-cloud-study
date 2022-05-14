package cn.jackiegu.spring.cloud.eureka.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试接口
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@RestController
@RequestMapping
public class EurekaClientController {

    @GetMapping("get")
    public Map<String, Object> get() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("success", true);
        return result;
    }
}
