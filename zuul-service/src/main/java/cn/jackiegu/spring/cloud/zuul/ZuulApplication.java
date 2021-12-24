package cn.jackiegu.spring.cloud.zuul;

import cn.jackiegu.spring.cloud.zuul.support.BeansSupport;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul网关启动类
 *
 * @author JackieGu
 * @date 2021/8/26
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {

    @Autowired
    private RefreshScope refreshScope;

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    /**
     * 热更新相关配置
     */
    @ApolloConfigChangeListener
    public void applicationConfigOnChange(ConfigChangeEvent changeEvent) {
        boolean zuulPropertiesChanged = false;
        for (String changedKey: changeEvent.changedKeys()) {
            if (changedKey.startsWith("zuul.")) {
                zuulPropertiesChanged = true;
                break;
            }
        }

        if (zuulPropertiesChanged) {
            refreshScope.refresh(BeansSupport.ZUUL_PROPERTIES_BEAN);
        }
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
