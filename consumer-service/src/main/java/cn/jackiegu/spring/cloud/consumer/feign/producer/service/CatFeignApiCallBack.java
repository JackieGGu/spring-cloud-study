package cn.jackiegu.spring.cloud.consumer.feign.producer.service;

import cn.jackiegu.spring.cloud.consumer.model.CatDTO;
import org.springframework.stereotype.Component;

/**
 * 生产者 猫咪 Hystrix降级
 *
 * @author JackieGu
 * @date 2021/8/8
 */
@Component
public class CatFeignApiCallBack implements CatFeignApi {

    @Override
    public CatDTO generate(Integer id) {
        return CatDTO.builder().id(-1).name("hystrix服务降级").build();
    }
}
