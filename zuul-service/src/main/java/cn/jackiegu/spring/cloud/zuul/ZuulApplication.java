package cn.jackiegu.spring.cloud.zuul;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.scope.refresh.RefreshScope;

/**
 * Zuul网关启动类
 *
 * @author JackieGu
 * @date 2021/8/26
 */
@EnableApolloConfig({ConfigConsts.NAMESPACE_APPLICATION, "spring-cloud-zuul.user"})
@SpringBootApplication
public class ZuulApplication {

    @Autowired
    private RefreshScope refreshScope;

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    /**
     * 监听spring-cloud-zuul.user namespace发布事件
     * 当事件发生时通知对应配置类去重新加载配置, 但是是在该配置类被调用时才会去重新加载配置
     */
    @ApolloConfigChangeListener("spring-cloud-zuul.user")
    public void userConfigOnChange(ConfigChangeEvent changeEvent) {
        refreshScope.refresh("userConfig");
    }
}
