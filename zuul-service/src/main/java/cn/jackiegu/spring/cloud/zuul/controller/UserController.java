package cn.jackiegu.spring.cloud.zuul.controller;

import cn.jackiegu.spring.cloud.zuul.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author JackieGu
 * @date 2021/8/26
 */
@RestController
@RequestMapping("user")
public class UserController {

    /**
     * Value注解配置将自动热更新
     */
    @Value("${area:中国}")
    private String area;

    @Autowired
    private UserConfig userConfig;

    @GetMapping("getName")
    public String getName() {
        return userConfig.getNickname();
    }

    @GetMapping("getAge")
    public Integer getAge() {
        return userConfig.getAge();
    }

    @GetMapping("getSex")
    public String getSex() {
        return userConfig.getSex();
    }

    @GetMapping("getArea")
    public String getArea() {
        return area;
    }
}
