package com.accountManager.account.accountSubType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class AccountSubTypeService implements AccountSubTypeInterface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private AccountSubTypeRepository accountSubTypeRepository;

    public AccountSubTypeService(AccountSubTypeRepository accountSubTypeRepository) {
        this.accountSubTypeRepository = accountSubTypeRepository;
    }

    @Override
    @Transactional
    public AccountSubTypeEntity saveAccountSubType(AccountSubTypeEntity accountSubType) {
        try {
            int msgResult = accountSubTypeRepository.checkAccountSubTypeName(accountSubType.getAccountSubType());
            if (msgResult > 0) {
                throw new RuntimeException("Account sub type already exists");
            }

            AccountSubTypeEntity save = accountSubTypeRepository.save(accountSubType);
            return save;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("Error while saving account sub type", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public AccountSubTypeEntity getAccountSubTypeById(Long id) {
        return accountSubTypeRepository.findById(id).orElse(null);
    }

    @Override
    public AccountSubTypeEntity getAccountSubTypeByName(String accountSubType) {
        return accountSubTypeRepository.findByAccountSubType(accountSubType);
    }

    @Override
    public List<AccountSubTypeEntity> getAllAccountSubTypes() {
        return accountSubTypeRepository.findAll();
    }

    @Override
    public List<AccountSubTypeEntity> getAccountSubTypesByAccountTypeId(Long accountTypeId) {
        return accountSubTypeRepository.findByAccountTypeId(accountTypeId);
    }

    @Override
    @Transactional
    public AccountSubTypeEntity updateAccountSubType(AccountSubTypeEntity accountSubType) {
        if (accountSubType.getAccountSubTypeId() == null || accountSubType.getAccountSubTypeId() <= 0)
            throw new RuntimeException("Invalid account sub type id");
        return saveAccountSubType(accountSubType);
    }

    @Override
    @Transactional
    public boolean deleteAccountSubType(Long id) {
        if (id == null || id <= 0)
            throw new RuntimeException("Invalid account sub type id");
        if (accountSubTypeRepository.existsById(id)) {
            accountSubTypeRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Account sub type not exist");
        }
    }

}
