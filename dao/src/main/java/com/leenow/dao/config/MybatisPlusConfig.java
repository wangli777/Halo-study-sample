package com.leenow.dao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangLi
 * @date 2021/1/31 16:08
 * @description
 */
@Configuration
@MapperScan("com.leenow.dao.mapper")
public class MybatisPlusConfig {
}
