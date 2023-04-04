package cn.jackiegu.spring.cloud.eureka.feign.hystrix.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 模拟为消费者启动类
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@SpringBootApplication
@EnableFeignClients
// 开启熔断监控页面
@EnableHystrixDashboard
// 开启hystrix.stream数据支持
@EnableCircuitBreaker
@EnableEurekaClient
public class EurekaFeignHystrixClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaFeignHystrixClientApplication.class, args);
    }
}
