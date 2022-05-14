package cn.jackiegu.spring.cloud.eureka.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户实体
 *
 * @author JackieGu
 * @date 2022/5/14
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private String name;

    private Integer age;

    private String sex;
}
