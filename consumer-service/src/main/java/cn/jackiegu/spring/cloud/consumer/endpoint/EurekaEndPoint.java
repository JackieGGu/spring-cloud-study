package cn.jackiegu.spring.cloud.consumer.endpoint;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.stereotype.Component;

/**
 * Eureka端点服务
 *
 * @author JackieGu
 * @date 2021/12/24
 */
@Component
@Endpoint(id = "eureka")
public class EurekaEndPoint {

    @Autowired
    private EurekaClient eurekaClient;

    @DeleteOperation
    public String offline() {
        eurekaClient.shutdown();
        return "success";
    }
}
