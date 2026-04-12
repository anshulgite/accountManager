package com.accountManager.transaction.expenseCategory;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "expenses_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExpensesCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "expenses_category_sequence", sequenceName = "expenses_category_sequence", allocationSize = 1)
    private Long expensesCategoryId;

    @Column(name = "expenses_category_name")
    private String expensesCategoryName;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
