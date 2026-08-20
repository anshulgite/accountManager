package com.accountManager.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class NotificationPublisher {
    Logger logger = LoggerFactory.getLogger(NotificationPublisher.class);
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public NotificationPublisher(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void  publish(Notification notification){

        try {
            String json = objectMapper.writeValueAsString(notification);
            redisTemplate.convertAndSend("notification", json);
        } catch (Exception e) {
            logger.error("Failed to publish notification", e);
        }
    }

}
