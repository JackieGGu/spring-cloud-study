package cn.jackiegu.spring.cloud.eureka.ribbon.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 模拟为消费者启动类
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@SpringBootApplication
@EnableEurekaClient
public class EurekaRibbonClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaRibbonClientApplication.class, args);
    }
}
