package com.accountManager.transaction.expense;

import java.util.List;

public interface ExpenseInterface {
    Expense saveExpense(Expense expense);
    Expense getExpenseById(Long id);
    List<Expense> getAllExpenses();
    Expense updateExpense(Expense expense);
    boolean deleteExpense(Long id);
}
