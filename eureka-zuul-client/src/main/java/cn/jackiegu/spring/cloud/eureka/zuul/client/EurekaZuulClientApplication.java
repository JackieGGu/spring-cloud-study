package cn.jackiegu.spring.cloud.eureka.zuul.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关启动类
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@SpringBootApplication
@EnableEurekaClient
// 开启Zuul代理
@EnableZuulProxy
// 开启hystrix.stream数据支持
@EnableCircuitBreaker
public class EurekaZuulClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaZuulClientApplication.class, args);
    }
}
