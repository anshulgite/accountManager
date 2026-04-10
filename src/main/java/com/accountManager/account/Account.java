package com.accountManager.account;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", allocationSize = 1)
    private Long accountId;

    @Column(name = "account_name")
    private String accountName;

    private long accountTypeId;

    private long accountSubTypeId;

    private long userId;

    private double balance;

    private double openingBalance;

    private double closingBalance;

    private String frequency;

    private boolean isPaymentAccount;

    private String paymentAccountType;

    private boolean isActive;

    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private long createdBy;

    @Column(name = "updated_by", nullable = false)
    private long updatedBy;



}
