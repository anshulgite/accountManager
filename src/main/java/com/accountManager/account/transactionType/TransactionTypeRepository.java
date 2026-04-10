package com.accountManager.account.transactionType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionTypeEntity, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM transaction_types WHERE transaction_type = :transactionType", nativeQuery = true)
    int checkTransactionTypeName(String transactionType);

    TransactionTypeEntity findByTransactionType(String transactionType);
}
