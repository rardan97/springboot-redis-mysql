package com.blaccode.spring_redis_mysql.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisConfigLogger {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfigLogger.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @PostConstruct
    public void logConfig() {
        logger.info("Redis Host: {}", redisHost);
        logger.info("Redis Port: {}", redisPort);
    }
}
