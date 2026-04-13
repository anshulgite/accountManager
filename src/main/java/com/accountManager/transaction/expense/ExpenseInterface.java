package com.accountManager.transaction.expense;

import org.springframework.security.core.Authentication;
import java.util.List;

public interface ExpenseInterface {

    public Expense createExpense(Expense expense);
    
    public List<Expense> getAllExpenses(Authentication authentication);
    
    public Expense getExpenseById(Long expenseId, Authentication authentication);
    
    public boolean deleteExpense(Long expenseId, Authentication authentication);
    
    public Expense updateExpense(Long expenseId, Expense expense, Authentication authentication);

}
