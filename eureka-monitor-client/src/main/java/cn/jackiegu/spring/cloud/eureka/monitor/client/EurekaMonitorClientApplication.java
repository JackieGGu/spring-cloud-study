package cn.jackiegu.spring.cloud.eureka.monitor.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * 模拟为消费者启动类
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
// 开启 Hystrix Turbine集群数据
@EnableTurbine
public class EurekaMonitorClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaMonitorClientApplication.class, args);
    }
}
