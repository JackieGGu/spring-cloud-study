package cn.jackiegu.spring.cloud.eureka.feign.client.service;

/**
 * Feign测试服务
 *
 * @author JackieGu
 * @date 2022/5/14
 */
public interface FeignService {

    String hello(String name);
}
