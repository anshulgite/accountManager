package com.accountManager.transaction.expense;

import com.accountManager.auth.refreshToken.CustomeUserDetails;
import com.accountManager.exception.ExceptionMassage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService implements ExpenseInterface {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense createExpense(Expense expense, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        assert user != null;
        Long userId = user.getUserId();
        expense.setCreatedBy(userId);
        expense.setUpdatedBy(userId);
        validateExpense(expense);
        return expenseRepository.save(expense);
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
    public boolean deleteExpense(Long expenseId,Authentication authentication)
    {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        assert user != null;
        Long userId = user.getUserId();
        //check if expense is created by user
        Expense expense = expenseRepository.findByExpenseIdAndCreatedBy(expenseId, userId);
        if (expense == null) {
            throw new IllegalArgumentException(ExceptionMassage.EXPENSE_NOT_FOUND);
        }
        expenseRepository.delete(expense);
        return true;
    }
    
    @Override
    public Expense updateExpense(Long expenseId, Expense expense, Authentication authentication) {
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
        
        return expenseRepository.save(existingExpense);
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

}
