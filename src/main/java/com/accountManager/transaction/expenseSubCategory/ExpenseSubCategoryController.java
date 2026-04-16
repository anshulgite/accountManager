package com.accountManager.transaction.expenseSubCategory;

import com.accountManager.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-sub-categories")
@Slf4j
public class ExpenseSubCategoryController {

    private final ExpenseSubCategoryInterface expenseSubCategoryService;

    public ExpenseSubCategoryController(ExpenseSubCategoryInterface expenseSubCategoryService) {
        this.expenseSubCategoryService = expenseSubCategoryService;
    }

    @PostMapping
    public ApiResponse<ExpenseSubCategory> createExpenseSubCategory(@RequestBody ExpenseSubCategory expenseSubCategory,Authentication authentication) {
        try {
            ExpenseSubCategory savedExpenseSubCategory = expenseSubCategoryService.saveExpenseSubCategory(expenseSubCategory,authentication);
            return ApiResponse.success(savedExpenseSubCategory, "Expense sub-category created successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<ExpenseSubCategory> getExpenseSubCategoryById(Authentication authentication, @PathVariable Long id) {
        try {
            ExpenseSubCategory expenseSubCategory = expenseSubCategoryService.getExpenseSubCategoryById(id, authentication);
            if (expenseSubCategory != null) {
                return ApiResponse.success(expenseSubCategory, "Expense sub-category found successfully");
            } else {
                return ApiResponse.error("Expense sub-category not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ApiResponse<List<ExpenseSubCategory>> getAllExpenseSubCategories(Authentication authentication) {
        try {
            List<ExpenseSubCategory> expenseSubCategories = expenseSubCategoryService.getAllExpenseSubCategories(authentication);
            return ApiResponse.success(expenseSubCategories, "Expense sub-categories retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{id}")
    public ApiResponse<List<ExpenseSubCategory>> getAllExpensesSubCategoryByCategoryId(@PathVariable Long id, Authentication authentication) {
        try {
            List<ExpenseSubCategory> expenseSubCategories = expenseSubCategoryService.getAllExpensesSubCategoryByCategoryId(id, authentication);
            return ApiResponse.success(expenseSubCategories, "Expense sub-categories retrieved successfully by category ID");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ApiResponse<ExpenseSubCategory> updateExpenseSubCategory(Authentication authentication, @RequestBody ExpenseSubCategory expenseSubCategory) {
        try {
            ExpenseSubCategory updatedExpenseSubCategory = expenseSubCategoryService.updateExpenseSubCategory(authentication, expenseSubCategory);
            return ApiResponse.success(updatedExpenseSubCategory, "Expense sub-category updated successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteExpenseSubCategory(Authentication authentication, @PathVariable Long id) {
        try {
            boolean deleted = expenseSubCategoryService.deleteExpenseSubCategory(id, authentication);
            if (deleted) {
                return ApiResponse.success("Expense sub-category deleted successfully");
            } else {
                return ApiResponse.error("Failed to delete expense sub-category", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
