package cn.jackiegu.spring.cloud.consumer.service.impl;

import cn.jackiegu.spring.cloud.consumer.model.CatDTO;
import cn.jackiegu.spring.cloud.consumer.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public CatDTO get() {
        CatDTO result;
        SecureRandom random = new SecureRandom();
        Map<String, Object> params = new HashMap<>();
        params.put("randomId", random.nextInt(1000));

        // 使用硬编码方式
        // result = restTemplate.getForObject("http://127.0.0.1:10021/producer/cat/generate?id={randomId}", CatDTO.class, params);

        // 使用注册中心方式
        List<ServiceInstance> instances = discoveryClient.getInstances("producer-service");
        ServiceInstance producerServiceInstance = instances.get(0);
        String url = "http://" +
            producerServiceInstance.getHost() +
            ":" +
            producerServiceInstance.getPort() +
            "/producer/cat/generate?id={randomId}";
        result = restTemplate.getForObject(url, CatDTO.class, params);
        return result;
    }
}
