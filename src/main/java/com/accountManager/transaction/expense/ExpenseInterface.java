package com.accountManager.transaction.expense;

import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public interface ExpenseInterface {

    public Expense createExpense(Expense expense, Authentication authentication, HttpServletRequest request);
    
    public List<Expense> getAllExpenses(Authentication authentication);
    
    public Expense getExpenseById(Long expenseId, Authentication authentication);
    
    public boolean deleteExpense(Long expenseId, Authentication authentication, HttpServletRequest request);
    
    public Expense updateExpense(Long expenseId, Expense expense, Authentication authentication, HttpServletRequest request);

}
