package com.accountManager.transaction.expenseSubCategory;

import com.accountManager.auth.AuthorizationUtils;
import com.accountManager.auth.refreshToken.CustomeUserDetails;
import com.accountManager.exception.ExceptionMassage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseSubCategoryService implements ExpenseSubCategoryInterface {

    @Autowired
    private ExpenseSubCategoryRepository expenseSubCategoryRepository;

    @Override
    public ExpenseSubCategory saveExpenseSubCategory(ExpenseSubCategory expenseSubCategory,Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        Long userId = user.getUserId();
        expenseSubCategory.setUserId(userId);
        return expenseSubCategoryRepository.save(expenseSubCategory);
    }

    @Override
    public ExpenseSubCategory getExpenseSubCategoryById(Long id, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        Long userId = user.getUserId();
        Optional<ExpenseSubCategory> expenseSubCategory = expenseSubCategoryRepository.findByExpenseSubCategoryIdAndUserId(id, userId);
        return expenseSubCategory.orElse(null);
    }

    @Override
    public List<ExpenseSubCategory> getAllExpenseSubCategories(Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        Long userId = user.getUserId();
        return expenseSubCategoryRepository.findAllByUserId(userId);
    }

    @Override
    public List<ExpenseSubCategory> getAllExpensesSubCategoryByCategoryId(Long categoryId, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        Long userId = user.getUserId();
        return expenseSubCategoryRepository.findByExpenseCategoryIdAndUserId(categoryId, userId);
    }

    @Override
    public ExpenseSubCategory updateExpenseSubCategory(Authentication authentication, ExpenseSubCategory expenseSubCategory) {
        ExpenseSubCategory existingExpenseSubCategory = expenseSubCategoryRepository.findById(expenseSubCategory.getExpenseSubCategoryId())
                .orElseThrow(() -> new RuntimeException("Expense sub-category not found"));
        AuthorizationUtils.validateUserAccess(authentication, existingExpenseSubCategory.getUserId());
        return expenseSubCategoryRepository.save(expenseSubCategory);
    }

    @Override
    public boolean deleteExpenseSubCategory(Long id, Authentication authentication) {
        if (expenseSubCategoryRepository.existsById(id)) {
            ExpenseSubCategory existingExpenseSubCategory = expenseSubCategoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Expense sub-category not found"));
            AuthorizationUtils.validateUserAccess(authentication, existingExpenseSubCategory.getUserId());
            expenseSubCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
