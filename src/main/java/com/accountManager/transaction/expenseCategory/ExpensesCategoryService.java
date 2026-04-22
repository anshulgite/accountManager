package com.accountManager.transaction.expenseCategory;

import com.accountManager.auth.AuthorizationUtils;
import com.accountManager.auth.refreshToken.CustomeUserDetails;
import com.accountManager.common.Validators;
import com.accountManager.eventAudit.EventAuditService;
import com.accountManager.exception.ExceptionMassage;
import com.accountManager.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpensesCategoryService implements ExpensesCategoryInterface {

    @Autowired
    private ExpensesCategoryRepository expensesCategoryRepository;
    
    @Autowired
    private EventAuditService eventAuditService;

    @Override
    public ExpensesCategory saveExpensesCategory(ExpensesCategory expensesCategory,Authentication authentication, HttpServletRequest request) {
        if(expensesCategory == null) {
            throw new RuntimeException(ExceptionMassage.EXPENSE_CATEGORY_CANNOT_BE_NULL);
        }
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        Long userId = user.getUserId();
        expensesCategory.setUserId(userId);
        
        ExpensesCategory savedCategory = expensesCategoryRepository.save(expensesCategory);
        
        // Get IP address and user agent
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        // Log audit event
        eventAuditService.logEvent(
            userId, 
            "EXPENSE_CATEGORY", 
            savedCategory.getExpensesCategoryId(), 
            "CREATE", 
            null, 
            savedCategory.toString(), 
            ipAddress, 
            userAgent
        );
        
        return savedCategory;
    }

    @Override
    public ExpensesCategory getExpensesCategoryById(Long id, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
           throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        Long userId = user.getUserId();
        Optional<ExpensesCategory> expensesCategory = expensesCategoryRepository.findByExpensesCategoryIdAndUserId(id,userId);
        return expensesCategory.orElse(null);
    }

    @Override
    public List<ExpensesCategory> getAllExpensesCategories(Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }

        Long userId = user.getUserId();
        //find all expenses categories by user id
        List<ExpensesCategory> expensesCategories= expensesCategoryRepository.findAllByUserId(userId);
        return expensesCategories;
    }

    @Override
    public ExpensesCategory updateExpensesCategory(Authentication authentication, ExpensesCategory expensesCategory, HttpServletRequest request) {
        ExpensesCategory expensesCategory1 = expensesCategoryRepository.findById(expensesCategory.getExpensesCategoryId()).orElseThrow(() -> new RuntimeException(ExceptionMassage.EXPENSE_CATEGORY_NOT_FOUND));
        AuthorizationUtils.validateUserAccess(authentication,expensesCategory1.getUserId());
        
        // Store old values for audit
        String oldValues = expensesCategory1.toString();
        
        ExpensesCategory updatedCategory = expensesCategoryRepository.save(expensesCategory);
        
        // Get user details
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        Long userId = user.getUserId();
        
        // Get IP address and user agent
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        // Log audit event
        eventAuditService.logEvent(
            userId, 
            "EXPENSE_CATEGORY", 
            updatedCategory.getExpensesCategoryId(), 
            "UPDATE", 
            oldValues, 
            updatedCategory.toString(), 
            ipAddress, 
            userAgent
        );
        
        return updatedCategory;
    }

    @Override
    public boolean deleteExpensesCategory(Long id, Authentication authentication, HttpServletRequest request) {
        if (expensesCategoryRepository.existsById(id)) {
            ExpensesCategory expensesCategory1 = expensesCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException(ExceptionMassage.EXPENSE_CATEGORY_NOT_FOUND));
            AuthorizationUtils.validateUserAccess(authentication,expensesCategory1.getUserId());
            
            // Get user details
            CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
            Long userId = user.getUserId();
            
            // Get IP address and user agent
            String ipAddress = getClientIpAddress(request);
            String userAgent = request.getHeader("User-Agent");
            
            // Log audit event before deletion
            eventAuditService.logEvent(
                userId, 
                "EXPENSE_CATEGORY", 
                id, 
                "DELETE", 
                expensesCategory1.toString(), 
                null, 
                ipAddress, 
                userAgent
            );
            
            expensesCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}
