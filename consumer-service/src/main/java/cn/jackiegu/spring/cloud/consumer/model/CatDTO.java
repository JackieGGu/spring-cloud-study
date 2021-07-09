package cn.jackiegu.spring.cloud.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 猫咪 传输实体
 *
 * @author JackieGu
 * @date 2021/7/9
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CatDTO {

    private Integer id;

    private String name;

    private String sex;

    private Integer age;
}
