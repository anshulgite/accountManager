package com.accountManager.transaction.expense;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "expense_sequence", sequenceName = "expense_sequence", allocationSize = 1)
    private Long expenseId;

    @Column(name = "expense_name")
    private String expenseName;

    @Column(name = "expense_amount")
    private Double expenseAmount;

    @Column(name = "expense_description")
    private String expenseDescription;

    @Column(name = "expense_date")
    private String expenseDate;

    @Column(name = "expense_time")
    private String expenseTime;

    @Column(name = "account")
    private Long accountId;

    @Column(name = "expense_type")
    private String expenseType;

    @Column(name = "expense_status")
    private String expenseStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    private Long createdBy;

    private Long updatedBy;





}
