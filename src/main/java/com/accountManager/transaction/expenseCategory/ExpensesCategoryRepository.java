package com.accountManager.transaction.expenseCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpensesCategoryRepository extends JpaRepository<ExpensesCategory, Long> {
    Optional<ExpensesCategory> findByExpensesCategoryIdAndUserId(Long id,Long userId);

    @Query("SELECT e FROM ExpensesCategory e WHERE e.userId = :userId")
    List<ExpensesCategory> findAllByUserId(Long userId);
}
