package com.accountManager.transaction.expenseSubCategory;

import com.accountManager.auth.AuthorizationUtils;
import com.accountManager.auth.refreshToken.CustomeUserDetails;
import com.accountManager.eventAudit.EventAuditService;
import com.accountManager.exception.ExceptionMassage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseSubCategoryService implements ExpenseSubCategoryInterface {

    @Autowired
    private ExpenseSubCategoryRepository expenseSubCategoryRepository;
    
    @Autowired
    private EventAuditService eventAuditService;

    @Override
    public ExpenseSubCategory saveExpenseSubCategory(ExpenseSubCategory expenseSubCategory,Authentication authentication, HttpServletRequest request) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        Long userId = user.getUserId();
        expenseSubCategory.setUserId(userId);
        
        ExpenseSubCategory savedSubCategory = expenseSubCategoryRepository.save(expenseSubCategory);
        
        // Get IP address and user agent
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        // Log audit event
        eventAuditService.logEvent(
            userId, 
            "EXPENSE_SUBCATEGORY", 
            savedSubCategory.getExpenseSubCategoryId(), 
            "CREATE", 
            null, 
            savedSubCategory.toString(), 
            ipAddress, 
            userAgent
        );
        
        return savedSubCategory;
    }

    @Override
    public ExpenseSubCategory getExpenseSubCategoryById(Long id, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        Long userId = user.getUserId();
        Optional<ExpenseSubCategory> expenseSubCategory = expenseSubCategoryRepository.findByExpenseSubCategoryIdAndUserId(id, userId);
        return expenseSubCategory.orElse(null);
    }

    @Override
    public List<ExpenseSubCategory> getAllExpenseSubCategories(Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        Long userId = user.getUserId();
        return expenseSubCategoryRepository.findAllByUserId(userId);
    }

    @Override
    public List<ExpenseSubCategory> getAllExpensesSubCategoryByCategoryId(Long categoryId, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        Long userId = user.getUserId();
        return expenseSubCategoryRepository.findByExpensesCategoryIdAndUserId(categoryId, userId);
    }

    @Override
    public ExpenseSubCategory updateExpenseSubCategory(Authentication authentication, ExpenseSubCategory expenseSubCategory, HttpServletRequest request) {
        ExpenseSubCategory existingExpenseSubCategory = expenseSubCategoryRepository.findById(expenseSubCategory.getExpenseSubCategoryId())
                .orElseThrow(() -> new RuntimeException("Expense sub-category not found"));
        AuthorizationUtils.validateUserAccess(authentication, existingExpenseSubCategory.getUserId());
        
        // Store old values for audit
        String oldValues = existingExpenseSubCategory.toString();
        
        ExpenseSubCategory updatedSubCategory = expenseSubCategoryRepository.save(expenseSubCategory);
        
        // Get user details
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        Long userId = user.getUserId();
        
        // Get IP address and user agent
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        // Log audit event
        eventAuditService.logEvent(
            userId, 
            "EXPENSE_SUBCATEGORY", 
            updatedSubCategory.getExpenseSubCategoryId(), 
            "UPDATE", 
            oldValues, 
            updatedSubCategory.toString(), 
            ipAddress, 
            userAgent
        );
        
        return updatedSubCategory;
    }

    @Override
    public boolean deleteExpenseSubCategory(Long id, Authentication authentication, HttpServletRequest request) {
        if (expenseSubCategoryRepository.existsById(id)) {
            ExpenseSubCategory existingExpenseSubCategory = expenseSubCategoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Expense sub-category not found"));
            AuthorizationUtils.validateUserAccess(authentication, existingExpenseSubCategory.getUserId());
            
            // Get user details
            CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
            Long userId = user.getUserId();
            
            // Get IP address and user agent
            String ipAddress = getClientIpAddress(request);
            String userAgent = request.getHeader("User-Agent");
            
            // Log audit event before deletion
            eventAuditService.logEvent(
                userId, 
                "EXPENSE_SUBCATEGORY", 
                id, 
                "DELETE", 
                existingExpenseSubCategory.toString(), 
                null, 
                ipAddress, 
                userAgent
            );
            
            expenseSubCategoryRepository.deleteById(id);
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
