package com.accountManager.notification.emailNotification;

import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {

    // Template 1 — Expense Category Added
    public String expenseCategoryTemplate(String categoryName, String userName) {
        return """
                Hi %s,
                
                A new expense category has been added: %s
                
                Login to your account to view details.
                
                Regards,
                Account Manager Team
                """.formatted(userName, categoryName);
    }

    // Template 2 — Transaction Alert
    public String transactionAlertTemplate(String amount, String type) {
        return """
                Transaction Alert!
                
                Type: %s
                Amount: %s
                
                If this was not you, please contact support immediately.
                
                Regards,
                Account Manager Team
                """.formatted(type, amount);
    }

    // Template 3 — Login Alert
    public String loginAlertTemplate(String userName, String ipAddress) {
        return """
                Hi %s,
                
                New login detected from IP: %s
                
                If this was not you, please secure your account immediately.
                
                Regards,
                Account Manager Team
                """.formatted(userName, ipAddress);
    }
}