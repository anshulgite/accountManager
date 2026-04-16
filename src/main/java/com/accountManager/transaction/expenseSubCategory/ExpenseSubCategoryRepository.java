package com.accountManager.transaction.expenseSubCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseSubCategoryRepository extends JpaRepository<ExpenseSubCategory, Long> {
    Optional<ExpenseSubCategory> findByExpenseSubCategoryIdAndUserId(Long id, Long userId);

    @Query("SELECT e FROM ExpenseSubCategory e WHERE e.userId = :userId")
    List<ExpenseSubCategory> findAllByUserId(Long userId);

    List<ExpenseSubCategory> findByExpenseCategoryIdAndUserId(Long categoryId, Long userId);
}
