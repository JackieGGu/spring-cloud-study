package cn.jackiegu.spring.cloud.producer.service.impl;

import cn.jackiegu.spring.cloud.producer.model.CatDTO;
import cn.jackiegu.spring.cloud.producer.service.CatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(CatServiceImpl.class);

    @Value("${server.port}")
    private String port;

    @Override
    public CatDTO generate(Integer id) {
        logger.info("Generate Cat entity, id: {}", id);
        if (id == 0) {
            try {
                Thread.sleep(5 * 1000);
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
