package com.accountManager.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM account WHERE account_name = :accountName", nativeQuery = true)
    int checkAccountName(String accountName);

    Account findByAccountName(String accountName);

    java.util.List<Account> findByAccountTypeId(Long accountTypeId);

    java.util.List<Account> findByAccountSubTypeId(Long accountSubTypeId);

    java.util.List<Account> findByIsActive(boolean isActive);

    java.util.List<Account> findByIsPaymentAccount(boolean isPaymentAccount);
}
