package com.accountManager.transaction.expenseCategory;

import com.accountManager.common.ApiResponse;
import com.accountManager.common.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses-categories")
@Slf4j
public class ExpensesCategoryController {

    private final ExpensesCategoryInterface expensesCategoryService;

    public ExpensesCategoryController(ExpensesCategoryInterface expensesCategoryService) {
        this.expensesCategoryService = expensesCategoryService;
    }

    @PostMapping
    public ApiResponse<ExpensesCategory> createExpensesCategory(@RequestBody ExpensesCategory expensesCategory,Authentication authentication) {
        try {
            ExpensesCategory savedExpensesCategory = expensesCategoryService.saveExpensesCategory(expensesCategory,authentication);
            return ApiResponse.success(savedExpensesCategory, "Expenses category created successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<ExpensesCategory> getExpensesCategoryById(Authentication authentication, @PathVariable Long id) {
        try {
            ExpensesCategory expensesCategory = expensesCategoryService.getExpensesCategoryById(id, authentication);
            if (expensesCategory != null) {
                return ApiResponse.success(expensesCategory, "Expenses category found successfully");
            } else {
                return ApiResponse.error("Expenses category not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ApiResponse<List<ExpensesCategory>> getAllExpensesCategories(Authentication authentication) {
        try {
            List<ExpensesCategory> expensesCategories = expensesCategoryService.getAllExpensesCategories(authentication);
            return ApiResponse.success(expensesCategories, "Expenses categories retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ApiResponse<ExpensesCategory> updateExpensesCategory(Authentication authentication, @RequestBody ExpensesCategory expensesCategory) {
        try {
            ExpensesCategory updatedExpensesCategory = expensesCategoryService.updateExpensesCategory(authentication, expensesCategory);
            return ApiResponse.success(updatedExpensesCategory, "Expenses category updated successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteExpensesCategory(Authentication authentication, @PathVariable Long id) {
        try {
            boolean deleted = expensesCategoryService.deleteExpensesCategory(id, authentication);
            if (deleted) {
                return ApiResponse.success("Expenses category deleted successfully");
            } else {
                return ApiResponse.error("Failed to delete expenses category", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
