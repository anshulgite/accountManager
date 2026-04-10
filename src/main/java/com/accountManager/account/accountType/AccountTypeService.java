package com.accountManager.account.accountType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class AccountTypeService implements AccountTypeInterface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private AccountTypeRepository accountTypeRepository;

    public AccountTypeService(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override
    @Transactional
    public AccountTypeEntity saveAccountType(AccountTypeEntity accountType) {
        try {
            int msgResult = accountTypeRepository.checkAccountTypeName(accountType.getAccountType());
            if (msgResult > 0) {
                throw new RuntimeException("Account type already exists");
            }

            AccountTypeEntity save = accountTypeRepository.save(accountType);
            return save;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("Error while saving account type", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public AccountTypeEntity getAccountTypeById(Long id) {
        return accountTypeRepository.findById(id).orElse(null);
    }

    @Override
    public AccountTypeEntity getAccountTypeByName(String accountType) {
        return accountTypeRepository.findByAccountType(accountType);
    }

    @Override
    public List<AccountTypeEntity> getAllAccountTypes() {
        return accountTypeRepository.findAll();
    }

    @Override
    @Transactional
    public AccountTypeEntity updateAccountType(AccountTypeEntity accountType) {
        if (accountType.getAccountTypeId() == null || accountType.getAccountTypeId() <= 0)
            throw new RuntimeException("Invalid account type id");
        return saveAccountType(accountType);
    }

    @Override
    @Transactional
    public boolean deleteAccountType(Long id) {
        if (id == null || id <= 0)
            throw new RuntimeException("Invalid account type id");
        if (accountTypeRepository.existsById(id)) {
            accountTypeRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Account type not exist");
        }
    }

}
