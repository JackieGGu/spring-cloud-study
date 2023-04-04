package cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.service;

import cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.entity.UserEntity;

import java.util.Map;

/**
 * Feign测试服务
 *
 * @author JackieGu
 * @date 2022/5/14
 */
public interface FeignHystrixService {

    String hello(String name);

    Map<String, Object> add(UserEntity user);
}
