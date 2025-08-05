package com.blaccode.spring_redis_mysql.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvChecker implements CommandLineRunner {

    private final Environment env;

    public EnvChecker(Environment env) {
        this.env = env;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("spring.redis.host = " + env.getProperty("spring.redis.host"));
        System.out.println("spring.redis.port = " + env.getProperty("spring.redis.port"));
    }
}
