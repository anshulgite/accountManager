package com.accountManager.transaction.expense;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseInterface expenseService;

    public ExpenseController(ExpenseInterface expenseService) {
        this.expenseService = expenseService;
    }
}
