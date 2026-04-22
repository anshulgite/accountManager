package com.accountManager.transaction.expense;

import com.accountManager.auth.refreshToken.CustomeUserDetails;
import com.accountManager.eventAudit.EventAuditService;
import com.accountManager.exception.ExceptionMassage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService implements ExpenseInterface {

    private final ExpenseRepository expenseRepository;
    private final EventAuditService eventAuditService;

    public ExpenseService(ExpenseRepository expenseRepository, EventAuditService eventAuditService) {
        this.expenseRepository = expenseRepository;
        this.eventAuditService = eventAuditService;
    }

    @Override
    public Expense createExpense(Expense expense, Authentication authentication, HttpServletRequest request) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        assert user != null;
        Long userId = user.getUserId();
        expense.setCreatedBy(userId);
        expense.setUpdatedBy(userId);
        validateExpense(expense);
        
        Expense savedExpense = expenseRepository.save(expense);
        
        // Get IP address and user agent
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        // Log audit event
        eventAuditService.logEvent(
            userId, 
            "EXPENSE", 
            savedExpense.getExpenseId(), 
            "CREATE", 
            null, 
            savedExpense.toString(), 
            ipAddress, 
            userAgent
        );
        
        return savedExpense;
    }

    @Override
    public List<Expense> getAllExpenses(Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        assert user != null;
        Long userId = user.getUserId();
        //get all expense by userId
        return expenseRepository.findByCreatedBy(userId);
    }

    @Override
    public Expense getExpenseById(Long expenseId, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        assert user != null;
        Long userId = user.getUserId();
        //check if expense is created by user
        Expense expense = expenseRepository.findByExpenseIdAndCreatedBy(expenseId, userId);
        if (expense == null) {
            throw new IllegalArgumentException(ExceptionMassage.EXPENSE_NOT_FOUND);
        }
        return expense;
    }

    @Override
    public boolean deleteExpense(Long expenseId, Authentication authentication, HttpServletRequest request)
    {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        assert user != null;
        Long userId = user.getUserId();
        //check if expense is created by user
        Expense expense = expenseRepository.findByExpenseIdAndCreatedBy(expenseId, userId);
        if (expense == null) {
            throw new IllegalArgumentException(ExceptionMassage.EXPENSE_NOT_FOUND);
        }
        
        // Get IP address and user agent
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        // Log audit event before deletion
        eventAuditService.logEvent(
            userId, 
            "EXPENSE", 
            expenseId, 
            "DELETE", 
            expense.toString(), 
            null, 
            ipAddress, 
            userAgent
        );
        
        expenseRepository.delete(expense);
        return true;
    }
    
    @Override
    public Expense updateExpense(Long expenseId, Expense expense, Authentication authentication, HttpServletRequest request) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        assert user != null;
        Long userId = user.getUserId();
        
        // Validate the expense object
        validateExpense(expense);
        
        // Check if the expense exists and belongs to the user
        Expense existingExpense = expenseRepository.findByExpenseIdAndCreatedBy(expenseId, userId);
        if (existingExpense == null) {
            throw new IllegalArgumentException(ExceptionMassage.EXPENSE_NOT_FOUND);
        }
        
        // Store old values for audit
        String oldValues = existingExpense.toString();
        
        // Update the expense fields
        existingExpense.setExpenseName(expense.getExpenseName());
        existingExpense.setExpenseAmount(expense.getExpenseAmount());
        existingExpense.setExpenseDescription(expense.getExpenseDescription());
        existingExpense.setExpenseDate(expense.getExpenseDate());
        existingExpense.setExpenseTime(expense.getExpenseTime());
        existingExpense.setAccountId(expense.getAccountId());
        existingExpense.setExpenseCategoryId(expense.getExpenseCategoryId());
        existingExpense.setPaymentMode(expense.getPaymentMode());
        existingExpense.setExpenseSubCategoryId(expense.getExpenseSubCategoryId());
        existingExpense.setExpenseType(expense.getExpenseType());
        existingExpense.setExpenseStatus(expense.getExpenseStatus());
        existingExpense.setUpdatedBy(userId);
        
        Expense updatedExpense = expenseRepository.save(existingExpense);
        
        // Get IP address and user agent
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        
        // Log audit event
        eventAuditService.logEvent(
            userId, 
            "EXPENSE", 
            expenseId, 
            "UPDATE", 
            oldValues, 
            updatedExpense.toString(), 
            ipAddress, 
            userAgent
        );
        
        return updatedExpense;
    }



    private void validateExpense(Expense expense) {

        if (expense == null) {
            throw new IllegalArgumentException(ExceptionMassage.EXPENSE_CANNOT_BE_NULL);
        }
        
        if (expense.getExpenseName() == null || expense.getExpenseName().trim().isEmpty()) {
            throw new IllegalArgumentException(ExceptionMassage.EXPENSE_NAME_IS_REQUIRED);
        }
        
        if (expense.getExpenseAmount() == null || expense.getExpenseAmount() <= 0) {
            throw new IllegalArgumentException(ExceptionMassage.EXPENSE_AMOUNT_MUST_BE_POSITIVE);
        }
        
        if (expense.getExpenseCategoryId() == null) {
            throw new IllegalArgumentException(ExceptionMassage.EXPENSE_CATEGORY_IS_REQUIRED);
        }
        
        if (expense.getPaymentMode() == null) {
            throw new IllegalArgumentException(ExceptionMassage.PAYMENT_MODE_IS_REQUIRED);
        }
        
        if (expense.getExpenseDate() != null && !isValidDate(expense.getExpenseDate())) {
            throw new IllegalArgumentException(ExceptionMassage.INVALID_EXPENSE_DATE_FORMAT);
        }
        
        if (expense.getExpenseTime() != null && !isValidTime(expense.getExpenseTime())) {
            throw new IllegalArgumentException(ExceptionMassage.INVALID_EXPENSE_TIME_FORMAT);
        }
    }
    
    private boolean isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) return true;
        try {
            java.time.LocalDate.parse(date);
            return true;
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }
    
    private boolean isValidTime(String time) {
        if (time == null || time.trim().isEmpty()) return true;
        try {
            java.time.LocalTime.parse(time);
            return true;
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
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
