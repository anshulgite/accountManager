package com.accountManager.transaction.expenseSubCategory;

import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ExpenseSubCategoryInterface {
    ExpenseSubCategory saveExpenseSubCategory(ExpenseSubCategory expenseSubCategory,Authentication authentication, HttpServletRequest request);
    ExpenseSubCategory getExpenseSubCategoryById(Long id, Authentication authentication);
    List<ExpenseSubCategory> getAllExpenseSubCategories(Authentication authentication);
    List<ExpenseSubCategory> getAllExpensesSubCategoryByCategoryId(Long id, Authentication authentication);
    ExpenseSubCategory updateExpenseSubCategory(Authentication authentication, ExpenseSubCategory expenseSubCategory, HttpServletRequest request);
    boolean deleteExpenseSubCategory(Long id, Authentication authentication, HttpServletRequest request);
}
