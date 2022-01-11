package cn.jackiegu.spring.cloud.zuul.listener;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.HashMap;
import java.util.Map;

/**
 * Apollo启动监听器
 *
 * @author JackieGu
 * @date 2022/1/10
 */
@Slf4j
public class ApolloRunListener implements SpringApplicationRunListener {

    /**
     * 是否已执行初始化
     */
    private static boolean initialize = false;

    public ApolloRunListener(SpringApplication application, String[] args) {
        log.info("ApolloSpringApplicationRunListener is being instantiated");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        if (initialize) {
            return;
        }
        initialize = true;
        AbstractEnvironment env = (AbstractEnvironment) environment;
        if (env.getActiveProfiles().length == 0) {
            // 若没有激活任何配置文件, 则默认激活local配置文件
            env.getSystemProperties().put("spring.profiles.active", "local");
        } else {
            // 若环境中包含Apollo Mata信息, 则进行一系列默认设定
            String apolloMetaEnv = environment.getProperty("APOLLO_META");
            String apolloMetaPro = environment.getProperty("apollo.meta");
            if (StrUtil.isNotBlank(apolloMetaEnv) || StrUtil.isNotBlank(apolloMetaPro)) {
                Map<String, Object> source = new HashMap<>();
                // 启用Apollo配置
                source.put("apollo.bootstrap.enabled", "true");
                // 日志系统初始化之前加载Apollo配置
                source.put("apollo.bootstrap.eagerLoad.enabled", "true");
                // 保证内存中的配置顺序与Portal页面一致
                source.put("apollo.property.order.enable", "true");
                // 设置配置本地缓存路径
                source.put("apollo.cacheDir", "D:\\Project\\cache\\apollo");
                env.getSystemProperties().putAll(source);
            }
        }
    }
}
