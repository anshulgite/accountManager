package com.accountManager.transaction.expenseSubCategory;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface ExpenseSubCategoryInterface {
    ExpenseSubCategory saveExpenseSubCategory(ExpenseSubCategory expenseSubCategory,Authentication authentication);
    ExpenseSubCategory getExpenseSubCategoryById(Long id, Authentication authentication);
    List<ExpenseSubCategory> getAllExpenseSubCategories(Authentication authentication);
    ExpenseSubCategory updateExpenseSubCategory(Authentication authentication, ExpenseSubCategory expenseSubCategory);
    boolean deleteExpenseSubCategory(Long id, Authentication authentication);
}
