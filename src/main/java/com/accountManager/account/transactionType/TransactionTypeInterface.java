package com.accountManager.account.transactionType;

import java.util.List;

public interface TransactionTypeInterface {

    public TransactionTypeEntity saveTransactionType(TransactionTypeEntity transactionType);
    public TransactionTypeEntity getTransactionTypeById(Long id);
    public TransactionTypeEntity getTransactionTypeByName(String transactionType);
    public List<TransactionTypeEntity> getAllTransactionTypes();
    public TransactionTypeEntity updateTransactionType(TransactionTypeEntity transactionType);
    public boolean deleteTransactionType(Long id);
}
