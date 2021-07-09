package cn.jackiegu.spring.cloud.producer.service;

import cn.jackiegu.spring.cloud.producer.model.CatDTO;

public interface CatService {

    CatDTO generate(Integer id);
}
