package com.accountManager.transaction.expenseSubCategory;

import java.util.List;

public interface ExpenseSubCategoryInterface {
    ExpenseSubCategory saveExpenseSubCategory(ExpenseSubCategory expenseSubCategory);
    ExpenseSubCategory getExpenseSubCategoryById(Long id);
    List<ExpenseSubCategory> getAllExpenseSubCategories();
    ExpenseSubCategory updateExpenseSubCategory(ExpenseSubCategory expenseSubCategory);
    boolean deleteExpenseSubCategory(Long id);
}
