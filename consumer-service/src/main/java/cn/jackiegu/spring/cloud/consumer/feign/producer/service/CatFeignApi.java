package cn.jackiegu.spring.cloud.consumer.feign.producer.service;

import cn.jackiegu.spring.cloud.consumer.model.CatDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 生产者 猫咪 Feign客户端
 *
 * @author JackieGu
 * @date 2021/8/7
 */
@FeignClient("producer-service")
@RequestMapping("producer")
public interface CatFeignApi {

    @GetMapping("cat/generate/{id}")
    CatDTO generate(@PathVariable("id") Integer id);
}
