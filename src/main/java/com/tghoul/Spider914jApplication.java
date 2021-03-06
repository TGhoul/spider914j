package com.tghoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author tghoul
 * @date 2017/11/23 17:28
 *
 * 应用启动类
 */
@SpringBootApplication
@EnableScheduling
public class Spider914jApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Spider914jApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Spider914jApplication.class, args);
    }
}
