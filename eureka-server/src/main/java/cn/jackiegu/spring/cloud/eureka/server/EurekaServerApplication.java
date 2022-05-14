package cn.jackiegu.spring.cloud.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka 注册服务中心
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@SpringBootApplication
// 开启服务注册中心
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
