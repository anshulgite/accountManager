package com.accountManager.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class NotificationSubscriber {

    Logger logger = LoggerFactory.getLogger(NotificationSubscriber.class);

    private final ObjectMapper objectMapper;

    public NotificationSubscriber(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void onMessage(String massage) {
        Notification notification = objectMapper.readValue(massage, Notification.class);
        logger.info("Notification received: {}", notification);
    }

}
