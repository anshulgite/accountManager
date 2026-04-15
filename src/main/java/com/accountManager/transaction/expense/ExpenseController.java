package com.accountManager.transaction.expense;

import com.accountManager.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@Slf4j
public class ExpenseController {

    private final ExpenseInterface expenseService;

    public ExpenseController(ExpenseInterface expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ApiResponse<Expense> createExpense(@RequestBody Expense expense,Authentication authentication) {
        try {
            log.info("Creating expense: {}", expense);
            if (expense == null) {
                return ApiResponse.error("Expense data is required", HttpStatus.BAD_REQUEST);
            }
            Expense savedExpense = expenseService.createExpense(expense,authentication);
            return ApiResponse.success(savedExpense, "Expense created successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ApiResponse<List<Expense>> getAllExpenses(Authentication authentication) {
        try {
            List<Expense> expenses = expenseService.getAllExpenses(authentication);
            return ApiResponse.success(expenses, "Expenses retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<Expense> getExpenseById(@PathVariable Long id, Authentication authentication) {
        try {
            Expense expense = expenseService.getExpenseById(id, authentication);
            if (expense != null) {
                return ApiResponse.success(expense, "Expense found successfully");
            } else {
                return ApiResponse.error("Expense not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense, Authentication authentication) {
        try {
            Expense updatedExpense = expenseService.updateExpense(id, expense, authentication);
            return ApiResponse.success(updatedExpense, "Expense updated successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteExpense(@PathVariable Long id, Authentication authentication) {
        try {
            boolean deleted = expenseService.deleteExpense(id, authentication);
            if (deleted) {
                return ApiResponse.success("Expense deleted successfully");
            } else {
                return ApiResponse.error("Failed to delete expense", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
