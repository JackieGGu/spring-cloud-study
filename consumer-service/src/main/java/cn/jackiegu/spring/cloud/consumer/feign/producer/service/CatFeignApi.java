package cn.jackiegu.spring.cloud.consumer.feign.producer.service;

import cn.jackiegu.spring.cloud.consumer.model.CatDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 生产者 猫咪 Feign客户端
 *
 * @author JackieGu
 * @date 2021/8/7
 */
@Primary
@FeignClient(value = "producer-service", fallback = CatFeignApiCallBack.class)
public interface CatFeignApi {

    @GetMapping("/producer/cat/generate/{id}")
    CatDTO generate(@PathVariable("id") Integer id);
}
