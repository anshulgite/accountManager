package com.accountManager.transaction.expenseCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpensesCategoryService implements ExpensesCategoryInterface {

    @Autowired
    private ExpensesCategoryRepository expensesCategoryRepository;

    @Override
    public ExpensesCategory saveExpensesCategory(ExpensesCategory expensesCategory) {
        return expensesCategoryRepository.save(expensesCategory);
    }

    @Override
    public ExpensesCategory getExpensesCategoryById(Long id) {
        Optional<ExpensesCategory> expensesCategory = expensesCategoryRepository.findById(id);
        return expensesCategory.orElse(null);
    }

    @Override
    public List<ExpensesCategory> getAllExpensesCategories() {
        return expensesCategoryRepository.findAll();
    }

    @Override
    public ExpensesCategory updateExpensesCategory(ExpensesCategory expensesCategory) {
        return expensesCategoryRepository.save(expensesCategory);
    }

    @Override
    public boolean deleteExpensesCategory(Long id) {
        if (expensesCategoryRepository.existsById(id)) {
            expensesCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
