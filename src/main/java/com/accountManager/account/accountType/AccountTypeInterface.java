package com.accountManager.account.accountType;

import java.util.List;

public interface AccountTypeInterface {

    public AccountTypeEntity saveAccountType(AccountTypeEntity accountType);
    public AccountTypeEntity getAccountTypeById(Long id);
    public AccountTypeEntity getAccountTypeByName(String accountType);
    public List<AccountTypeEntity> getAllAccountTypes();
    public AccountTypeEntity updateAccountType(AccountTypeEntity accountType);
    public boolean deleteAccountType(Long id);
}
