package com.accountManager.account.accountType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountTypeEntity, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM account_types WHERE account_type = :accountType", nativeQuery = true)
    int checkAccountTypeName(String accountType);

    AccountTypeEntity findByAccountType(String accountType);
}
