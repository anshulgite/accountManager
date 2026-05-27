package com.accountManager.accountTransaction;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_transaction_sequence")
    @SequenceGenerator(name = "account_transaction_sequence", sequenceName = "account_transaction_sequence", allocationSize = 1,initialValue = 1)
    private Long id;

    @Column(name = "from_account_id", nullable = false)
    private Long FromAccountId;

    @Column(name = "to_account_id", nullable = false)
    private  Long ToAccountId;

    @Column(name = "transaction_type_id", nullable = false)
    private Long TransactionTypeId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_date", nullable = true)
    private LocalDate transactionDate;

    @Column(name = "transaction_time", nullable = true)
    private LocalTime transactionTime;

    @Column(name = "created_by", nullable = false,updatable = false)
    private Long CreatedBy;

    @Column(name = "updated_by", nullable = false)
    private Long UpdatedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
