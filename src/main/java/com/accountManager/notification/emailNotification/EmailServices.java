package com.accountManager.notification.emailNotification;

import com.accountManager.eventAudit.EventAudit;
import com.accountManager.eventAudit.EventAuditService;
import com.accountManager.user.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServices {

    private final JavaMailSender javaMailSender;
    private final UserService userServices;
    private final EventAuditService eventAudit;
    public EmailServices(JavaMailSender javaMailSender,UserService userServices,EventAuditService eventAudit) {
        this.javaMailSender = javaMailSender;
        this.userServices = userServices;
        this.eventAudit = eventAudit;
    }

    public void sendEmail(Long userId, String subject, String content) {

        try {

            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom("anshulgite2000@gmail.com");
            mailMessage.setTo(getUserEmail(userId));
            mailMessage.setSubject(subject);
            mailMessage.setText(content);
            javaMailSender.send(mailMessage);

        } catch (Exception e) {
           e.printStackTrace();
        }

    }

   private String getUserEmail(Long userId) {
        return userServices.getUserById(userId).getEmail();
    }
}

