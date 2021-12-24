package cn.jackiegu.spring.cloud.producer.service.impl;

import cn.jackiegu.spring.cloud.producer.model.CatDTO;
import cn.jackiegu.spring.cloud.producer.service.CatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 猫咪 Service
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@Slf4j
@Service
public class CatServiceImpl implements CatService {

    @Value("${server.port}")
    private String port;

    @Override
    public CatDTO generate(Integer id) {
        log.info("Generate Cat entity, id: {}", id);
        if (id > 1000) {
            try {
                Thread.sleep(id);
            } catch (Exception ignored) {
            }
        }
        return CatDTO.builder()
            .id(id)
            .name("qiuqiu")
            .sex("man")
            .age(4)
            .port(port)
            .build();
    }
}
