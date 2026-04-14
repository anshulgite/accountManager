package com.accountManager.transaction.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
   public List<Expense> findByCreatedBy(Long userId);

   public Expense findByExpenseIdAndCreatedBy(Long expenseId, Long userId);
}
