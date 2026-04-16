package com.accountManager.transaction.expenseCategory;

import com.accountManager.auth.AuthorizationUtils;
import com.accountManager.auth.refreshToken.CustomeUserDetails;
import com.accountManager.common.Validators;
import com.accountManager.exception.ExceptionMassage;
import com.accountManager.user.UserEntity;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpensesCategoryService implements ExpensesCategoryInterface {

    @Autowired
    private ExpensesCategoryRepository expensesCategoryRepository;

    @Override
    public ExpensesCategory saveExpensesCategory(ExpensesCategory expensesCategory,Authentication authentication) {
        if(expensesCategory == null) {
            throw new RuntimeException(ExceptionMassage.EXPENSE_CATEGORY_CANNOT_BE_NULL);
        }
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        expensesCategory.setUserId(user.getUserId());
        return expensesCategoryRepository.save(expensesCategory);
    }

    @Override
    public ExpensesCategory getExpensesCategoryById(Long id, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
           throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        Long userId = user.getUserId();
        Optional<ExpensesCategory> expensesCategory = expensesCategoryRepository.findByExpensesCategoryIdAndUserId(id,userId);
        return expensesCategory.orElse(null);
    }

    @Override
    public List<ExpensesCategory> getAllExpensesCategories(Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if(user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }

        Long userId = user.getUserId();
        //find all expenses categories by user id
        List<ExpensesCategory> expensesCategories= expensesCategoryRepository.findAllByUserId(userId);
        return expensesCategories;
    }

    @Override
    public ExpensesCategory updateExpensesCategory(Authentication authentication,ExpensesCategory expensesCategory) {
        ExpensesCategory expensesCategory1 = expensesCategoryRepository.findById(expensesCategory.getExpensesCategoryId()).orElseThrow(() -> new RuntimeException(ExceptionMassage.EXPENSE_CATEGORY_NOT_FOUND));
        AuthorizationUtils.validateUserAccess(authentication,expensesCategory1.getUserId());
        return expensesCategoryRepository.save(expensesCategory);
    }

    @Override
    public boolean deleteExpensesCategory(Long id, Authentication authentication) {
        if (expensesCategoryRepository.existsById(id)) {
            ExpensesCategory expensesCategory1 = expensesCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException(ExceptionMassage.EXPENSE_CATEGORY_NOT_FOUND));
            AuthorizationUtils.validateUserAccess(authentication,expensesCategory1.getUserId());
            expensesCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
