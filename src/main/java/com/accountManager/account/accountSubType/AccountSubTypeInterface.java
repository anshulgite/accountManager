package com.accountManager.account.accountSubType;

import java.util.List;

public interface AccountSubTypeInterface {

    public AccountSubTypeEntity saveAccountSubType(AccountSubTypeEntity accountSubType);
    public AccountSubTypeEntity getAccountSubTypeById(Long id);
    public AccountSubTypeEntity getAccountSubTypeByName(String accountSubType);
    public List<AccountSubTypeEntity> getAllAccountSubTypes();
    public List<AccountSubTypeEntity> getAccountSubTypesByAccountTypeId(Long accountTypeId);
    public AccountSubTypeEntity updateAccountSubType(AccountSubTypeEntity accountSubType);
    public boolean deleteAccountSubType(Long id);
}
