package cn.jackiegu.spring.cloud.eureka.feign.hystrix.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 生产者启动类
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@SpringBootApplication
// 开启Feign支持
@EnableFeignClients
// 以下两个注解都表示开启Eureka客户端(不配置时默认也会开启)
@EnableEurekaClient
// @EnableDiscoveryClient
public class EurekaFeignHystrixClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaFeignHystrixClientApplication.class, args);
    }
}
