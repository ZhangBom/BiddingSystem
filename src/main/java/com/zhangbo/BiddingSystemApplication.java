package com.zhangbo;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@MapperScan("com.zhangbo.mapper")
public class BiddingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiddingSystemApplication.class, args);
    }

}
