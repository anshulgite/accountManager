package com.accountManager.account.accountSubType;

import com.accountManager.account.accountType.AccountTypeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_sub_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountSubTypeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "account_sub_type_sequence", sequenceName = "account_sub_type_sequence", allocationSize = 1)
    private Long accountSubTypeId;

    @Column(name = "account_type_id", nullable = false)
    private long accountTypeId;

    @Column(name = "account_sub_type", nullable = false, unique = true)
    private String accountSubType;

    @Column(name = "nature", nullable = false)
    private String nature;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
