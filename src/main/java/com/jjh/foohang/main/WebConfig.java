package com.jjh.foohang.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.jjh.foohang.*.model.mapper")
public class WebConfig {

}
