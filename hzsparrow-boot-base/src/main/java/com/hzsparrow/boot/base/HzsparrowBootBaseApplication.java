package com.hzsparrow.boot.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hzsparrow.**.mapper")
public class HzsparrowBootBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(HzsparrowBootBaseApplication.class, args);
    }

}
