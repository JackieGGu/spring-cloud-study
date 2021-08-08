package cn.jackiegu.spring.cloud.consumer.controller;

import cn.jackiegu.spring.cloud.consumer.model.CatDTO;
import cn.jackiegu.spring.cloud.consumer.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 猫咪 Controller
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@RestController
@RequestMapping("cat")
public class CatController {

    @Autowired
    private CatService catService;

    @GetMapping("get")
    public CatDTO get(Boolean sleep, Long sleepTime) {
        return catService.get(sleep, sleepTime);
    }

    @GetMapping("hello")
    public String hello() {
        return "hello qiuqiu";
    }
}
