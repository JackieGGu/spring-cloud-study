package cn.jackiegu.spring.cloud.eureka.client.controller;

import cn.hutool.json.JSONUtil;
import cn.jackiegu.spring.cloud.eureka.client.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Value("${server.port}")
    private int port;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "成功");
        Map<String, Object> data = new HashMap<>();
        data.put("port", port);
        data.put("name", name);
        result.put("data", data);
        return JSONUtil.toJsonStr(result);
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody UserEntity user) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "成功");
        result.put("data", user);
        return result;
    }
}
