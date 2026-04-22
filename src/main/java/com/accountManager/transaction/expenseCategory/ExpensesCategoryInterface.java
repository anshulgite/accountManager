package com.accountManager.transaction.expenseCategory;

import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ExpensesCategoryInterface {
    ExpensesCategory saveExpensesCategory(ExpensesCategory expensesCategory,Authentication authentication, HttpServletRequest request);
    ExpensesCategory getExpensesCategoryById(Long id, Authentication authentication);
    List<ExpensesCategory> getAllExpensesCategories(Authentication authentication);
    ExpensesCategory updateExpensesCategory(Authentication authentication, ExpensesCategory expensesCategory, HttpServletRequest request);
    boolean deleteExpensesCategory(Long id, Authentication authentication, HttpServletRequest request);
}
