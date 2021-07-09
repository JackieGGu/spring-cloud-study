package cn.jackiegu.spring.cloud.consumer.service.impl;

import cn.jackiegu.spring.cloud.consumer.model.CatDTO;
import cn.jackiegu.spring.cloud.consumer.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * 猫咪 Service
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@Service
public class CatServiceImpl implements CatService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CatDTO get() {
        SecureRandom random = new SecureRandom();
        Map<String, Object> params = new HashMap<>();
        params.put("randomId", random.nextInt(1000));
        return restTemplate.getForObject("http://127.0.0.1:10021/producer/cat/generate?id={randomId}", CatDTO.class, params);
    }
}
