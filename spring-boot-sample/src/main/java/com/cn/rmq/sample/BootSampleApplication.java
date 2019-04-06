package com.cn.rmq.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Chen Nan
 */
@SpringBootApplication
@MapperScan("com.cn.rmq.sample.mapper")
public class BootSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootSampleApplication.class, args);
    }
}
