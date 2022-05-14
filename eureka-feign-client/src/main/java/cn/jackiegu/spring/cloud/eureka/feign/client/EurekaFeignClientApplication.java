package cn.jackiegu.spring.cloud.eureka.feign.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 模拟为消费者启动类
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@SpringBootApplication
// 开启Feign支持
@EnableFeignClients
@EnableEurekaClient
public class EurekaFeignClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaFeignClientApplication.class, args);
    }
}
