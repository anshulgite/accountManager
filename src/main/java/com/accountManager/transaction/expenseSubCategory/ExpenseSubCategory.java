package com.accountManager.transaction.expenseSubCategory;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "expense_sub_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExpenseSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "expense_sub_category_sequence", sequenceName = "expense_sub_category_sequence", allocationSize = 1)
    private Long expenseSubCategoryId;

    private String expenseSubCategoryName;

    private Long expensesCategoryId;
    
    @Column(name = "user_id")
    private Long userId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;


}
