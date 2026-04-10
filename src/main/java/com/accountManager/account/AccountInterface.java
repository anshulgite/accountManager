package com.accountManager.account;



import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountInterface {

    public Account saveAccount(Account account);
    public Account getAccountById(Long id, Authentication authentication);
    public Account getAccountByName(String accountName);
    public List<Account> getAllAccounts();
    public Account updateAccount(Account account);
    public boolean deleteAccount(Long id);

}
