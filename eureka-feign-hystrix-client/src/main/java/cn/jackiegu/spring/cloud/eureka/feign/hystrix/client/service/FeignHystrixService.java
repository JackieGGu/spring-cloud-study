package cn.jackiegu.spring.cloud.eureka.feign.hystrix.client.service;

/**
 * Feign测试服务
 *
 * @author JackieGu
 * @date 2022/5/14
 */
public interface FeignHystrixService {

    String hello(String name);
}
