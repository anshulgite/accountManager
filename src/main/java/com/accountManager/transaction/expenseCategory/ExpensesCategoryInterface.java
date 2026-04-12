package com.accountManager.transaction.expenseCategory;

import java.util.List;

public interface ExpensesCategoryInterface {
    ExpensesCategory saveExpensesCategory(ExpensesCategory expensesCategory);
    ExpensesCategory getExpensesCategoryById(Long id);
    List<ExpensesCategory> getAllExpensesCategories();
    ExpensesCategory updateExpensesCategory(ExpensesCategory expensesCategory);
    boolean deleteExpensesCategory(Long id);
}
