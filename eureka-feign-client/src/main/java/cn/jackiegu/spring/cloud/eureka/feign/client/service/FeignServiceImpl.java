package cn.jackiegu.spring.cloud.eureka.feign.client.service;

import cn.jackiegu.spring.cloud.eureka.feign.client.feign.FeignApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Feign测试服务实现
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@Service
public class FeignServiceImpl implements FeignService {

    @Autowired
    private FeignApi feignApi;

    @Override
    public String hello(String name) {
        return feignApi.hello(name);
    }
}
