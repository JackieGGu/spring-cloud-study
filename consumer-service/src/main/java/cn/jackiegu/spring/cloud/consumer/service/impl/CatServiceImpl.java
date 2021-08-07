package cn.jackiegu.spring.cloud.consumer.service.impl;

import cn.jackiegu.spring.cloud.consumer.feign.producer.service.CatFeignApi;
import cn.jackiegu.spring.cloud.consumer.model.CatDTO;
import cn.jackiegu.spring.cloud.consumer.service.CatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    Logger logger = LoggerFactory.getLogger(CatServiceImpl.class);

    // @Autowired
    // private DiscoveryClient discoveryClient;

    // @Autowired
    // private RestTemplate restTemplate;

    @Autowired
    private CatFeignApi catFeignApi;

    @Override
    public CatDTO get(Boolean sleep) {
        CatDTO result;
        logger.info("Request producer-service, sleep: {}", sleep);
        SecureRandom random = new SecureRandom();
        Map<String, Object> params = new HashMap<>();
        if (sleep == null || !sleep) {
            params.put("randomId", random.nextInt(1000) + 1);
        } else {
            params.put("randomId", 0);
        }

        // 使用硬编码方式
        // result = restTemplate.getForObject("http://127.0.0.1:10021/producer/cat/generate/{randomId}", CatDTO.class, params);

        // 使用注册中心方式
        // List<ServiceInstance> instances = discoveryClient.getInstances("producer-service");
        // ServiceInstance producerServiceInstance = instances.get(0);
        // String url = "http://" +
        //     producerServiceInstance.getHost() +
        //     ":" +
        //     producerServiceInstance.getPort() +
        //     "/producer/cat/generate?id={randomId}";
        // result = restTemplate.getForObject(url, CatDTO.class, params);

        // 使用Ribbon客户端负载均衡方式(默认以轮询方式)
        // result = restTemplate.getForObject("http://producer-service/producer/cat/generate/{randomId}", CatDTO.class, params);

        // 使用Feign组件
        result = catFeignApi.generate(Integer.parseInt(params.get("randomId").toString()));
        return result;
    }
}
