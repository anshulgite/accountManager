package com.accountManager.account.transactionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class TransactionTypeService implements TransactionTypeInterface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private TransactionTypeRepository transactionTypeRepository;

    public TransactionTypeService(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Override
    @Transactional
    public TransactionTypeEntity saveTransactionType(TransactionTypeEntity transactionType) {
        try {
            int msgResult = transactionTypeRepository.checkTransactionTypeName(transactionType.getTransactionType());
            if (msgResult > 0) {
                throw new RuntimeException("Transaction type already exists");
            }

            TransactionTypeEntity save = transactionTypeRepository.save(transactionType);
            return save;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("Error while saving transaction type", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public TransactionTypeEntity getTransactionTypeById(Long id) {
        return transactionTypeRepository.findById(id).orElse(null);
    }

    @Override
    public TransactionTypeEntity getTransactionTypeByName(String transactionType) {
        return transactionTypeRepository.findByTransactionType(transactionType);
    }

    @Override
    public List<TransactionTypeEntity> getAllTransactionTypes() {
        return transactionTypeRepository.findAll();
    }

    @Override
    @Transactional
    public TransactionTypeEntity updateTransactionType(TransactionTypeEntity transactionType) {
        if (transactionType.getTransactionTypeId() == null || transactionType.getTransactionTypeId() <= 0)
            throw new RuntimeException("Invalid transaction type id");
        return saveTransactionType(transactionType);
    }

    @Override
    @Transactional
    public boolean deleteTransactionType(Long id) {
        if (id == null || id <= 0)
            throw new RuntimeException("Invalid transaction type id");
        if (transactionTypeRepository.existsById(id)) {
            transactionTypeRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Transaction type not exist");
        }
    }

}
