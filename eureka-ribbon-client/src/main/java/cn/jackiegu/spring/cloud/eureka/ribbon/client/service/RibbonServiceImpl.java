package cn.jackiegu.spring.cloud.eureka.ribbon.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Ribbon测试服务实现
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@Service
public class RibbonServiceImpl implements RibbonService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String hello(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return restTemplate.getForObject("http://eureka-client/eureka/client/hello/{name}", String.class, params);
    }
}
