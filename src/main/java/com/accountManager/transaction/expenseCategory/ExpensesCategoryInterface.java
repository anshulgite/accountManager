package com.accountManager.transaction.expenseCategory;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface ExpensesCategoryInterface {
    ExpensesCategory saveExpensesCategory(ExpensesCategory expensesCategory,Authentication authentication);
    ExpensesCategory getExpensesCategoryById(Long id, Authentication authentication);
    List<ExpensesCategory> getAllExpensesCategories(Authentication authentication);
    ExpensesCategory updateExpensesCategory(Authentication authentication, ExpensesCategory expensesCategory);
    boolean deleteExpensesCategory(Long id, Authentication authentication);
}
