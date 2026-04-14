package com.accountManager.account.accountSubType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountSubTypeRepository extends JpaRepository<AccountSubTypeEntity, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM account_sub_types WHERE account_sub_type = :accountSubType", nativeQuery = true)
    int checkAccountSubTypeName(String accountSubType);

    AccountSubTypeEntity findByAccountSubType(String accountSubType);

    List<AccountSubTypeEntity> findByAccountTypeId(Long accountTypeId);
}
