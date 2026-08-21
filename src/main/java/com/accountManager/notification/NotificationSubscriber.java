package com.accountManager.notification;

import com.accountManager.notification.emailNotification.EmailServices;
import com.accountManager.notification.emailNotification.EmailTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class NotificationSubscriber {

    Logger logger = LoggerFactory.getLogger(NotificationSubscriber.class);

    private final ObjectMapper objectMapper;
    private final EmailTemplateService emailTemplateService;
    private final EmailServices emailServices;
    public NotificationSubscriber(ObjectMapper objectMapper,EmailTemplateService emailTemplateService,EmailServices emailServices) {
        this.objectMapper = objectMapper;
        this.emailTemplateService = emailTemplateService;
        this.emailServices = emailServices;
    }

    public void onMessage(String massage) {
        Notification notification = objectMapper.readValue(massage, Notification.class);
        logger.info("Notification received: {}", notification);

        String body = switch (notification.getType()) {
            case "EXPENSE_CATEGORY" -> emailTemplateService.expenseCategoryTemplate(notification.getType(), notification.getMessage());
            default -> notification.getMessage();
        };

        emailServices.sendEmail(notification.getUserId(), notification.getType(), body);

    }

}
