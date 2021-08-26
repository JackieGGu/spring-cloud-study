package cn.jackiegu.spring.cloud.zuul.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 用户配置
 * 读取Apollo中spring-cloud-zuul.user namespace配置
 * RefreshScope注解的作用: 针对使用ConfigurationProperties注解的情况下使热更新生效, 但必须配合ApolloConfigChangeListener注解同时使用
 *
 * @author JackieGu
 * @date 2021/8/26
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "user")
public class UserConfig {

    private String nickname;

    private Integer age;

    private String sex;
}
