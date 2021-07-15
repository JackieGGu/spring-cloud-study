package cn.jackiegu.spring.cloud.producer.service.impl;

import cn.jackiegu.spring.cloud.producer.model.CatDTO;
import cn.jackiegu.spring.cloud.producer.service.CatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 猫咪 Service
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@Service
public class CatServiceImpl implements CatService {

    @Value("${server.port}")
    private String port;

    @Override
    public CatDTO generate(Integer id) {
        return CatDTO.builder()
            .id(id)
            .name("球球")
            .sex("公")
            .age(4)
            .port(port)
            .build();
    }
}
