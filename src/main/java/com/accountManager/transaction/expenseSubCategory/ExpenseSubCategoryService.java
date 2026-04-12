package com.accountManager.transaction.expenseSubCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseSubCategoryService implements ExpenseSubCategoryInterface {

    @Autowired
    private ExpenseSubCategoryRepository expenseSubCategoryRepository;

    @Override
    public ExpenseSubCategory saveExpenseSubCategory(ExpenseSubCategory expenseSubCategory) {
        return expenseSubCategoryRepository.save(expenseSubCategory);
    }

    @Override
    public ExpenseSubCategory getExpenseSubCategoryById(Long id) {
        Optional<ExpenseSubCategory> expenseSubCategory = expenseSubCategoryRepository.findById(id);
        return expenseSubCategory.orElse(null);
    }

    @Override
    public List<ExpenseSubCategory> getAllExpenseSubCategories() {
        return expenseSubCategoryRepository.findAll();
    }

    @Override
    public ExpenseSubCategory updateExpenseSubCategory(ExpenseSubCategory expenseSubCategory) {
        return expenseSubCategoryRepository.save(expenseSubCategory);
    }

    @Override
    public boolean deleteExpenseSubCategory(Long id) {
        if (expenseSubCategoryRepository.existsById(id)) {
            expenseSubCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
