package cn.jackiegu.spring.cloud.consumer.service;

import cn.jackiegu.spring.cloud.consumer.model.CatDTO;

public interface CatService {

    CatDTO get(Boolean sleep, Long sleepTime);
}
