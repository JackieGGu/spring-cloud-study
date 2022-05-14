package cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.service;

import cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.entity.UserEntity;
import cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.feign.FeignHystrixApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Feign测试服务实现
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@Service
public class FeignHystrixServiceImpl implements FeignHystrixService {

    @Autowired
    private FeignHystrixApi feignHystrixApi;

    @Override
    public String hello(String name) {
        return feignHystrixApi.hello(name);
    }

    @Override
    public Map<String, Object> add(UserEntity user) {
        return feignHystrixApi.add(user);
    }
}
