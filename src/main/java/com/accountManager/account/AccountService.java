package com.accountManager.account;

import com.accountManager.auth.AuthorizationUtils;
import com.accountManager.exception.ExceptionMassage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService implements AccountInterface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    private void validateAccount(Account account) {
        if (account == null) {
            throw new RuntimeException(ExceptionMassage.ACCOUNT_CANNOT_BE_NULL);
        }
        if (account.getAccountName() == null || account.getAccountName().trim().isEmpty()) {
            throw new RuntimeException(ExceptionMassage.ACCOUNT_NAME_IS_REQUIRED);
        }
        if (account.getAccountTypeId() <= 0) {
            throw new RuntimeException(ExceptionMassage.ACCOUNT_TYPE_ID_MUST_BE_POSITIVE);
        }
        if (account.getAccountSubTypeId() <= 0) {
            throw new RuntimeException(ExceptionMassage.ACCOUNT_SUB_TYPE_ID_MUST_BE_POSITIVE);
        }
        if (account.getBalance() < 0) {
            throw new RuntimeException(ExceptionMassage.BALANCE_CANNOT_BE_NEGATIVE);
        }
        if (account.getOpeningBalance() < 0) {
            throw new RuntimeException(ExceptionMassage.OPENING_BALANCE_CANNOT_BE_NEGATIVE);
        }
        if (account.getClosingBalance() < 0) {
            throw new RuntimeException(ExceptionMassage.CLOSING_BALANCE_CANNOT_BE_NEGATIVE);
        }
        int existingAccount = accountRepository.checkAccountName(account.getAccountName());
        if (existingAccount > 0) {
            throw new RuntimeException(ExceptionMassage.ACCOUNT_NAME_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional
    public Account saveAccount(Account account) {
       try {
           validateAccount(account);
           return accountRepository.save(account);
       } catch (Exception e) {
          throw new RuntimeException(e.getMessage());
       }
    }

    @Override
    public Account getAccountById(Long id, Authentication authentication) {
        Account account = accountRepository.findById(id).orElse(null);
        
        // Validate user ID here
        if (account != null) {
            AuthorizationUtils.validateUserAccess(authentication, account.getUserId());
        }

        return account;
    }

    @Override
    public Account getAccountByName(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public boolean deleteAccount(Long id) {
         if(accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
         }
         else {
             throw new RuntimeException(ExceptionMassage.ACCOUNT_NOT_FOUND);
         }

    }


}
