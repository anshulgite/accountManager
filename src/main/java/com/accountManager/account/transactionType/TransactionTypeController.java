package com.accountManager.account.transactionType;

import com.accountManager.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction-types")
public class TransactionTypeController {

    private final TransactionTypeInterface transactionTypeService;

    public TransactionTypeController(TransactionTypeInterface transactionTypeService) {
        this.transactionTypeService = transactionTypeService;
    }

    @PostMapping
    public ApiResponse<TransactionTypeEntity> createTransactionType(@RequestBody TransactionTypeEntity transactionType) {
        try {
            TransactionTypeEntity savedTransactionType = transactionTypeService.saveTransactionType(transactionType);
            return ApiResponse.success(savedTransactionType, "Transaction type created successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<TransactionTypeEntity> getTransactionTypeById(@PathVariable Long id) {
        try {
            TransactionTypeEntity transactionType = transactionTypeService.getTransactionTypeById(id);
            if (transactionType != null) {
                return ApiResponse.success(transactionType, "Transaction type found successfully");
            } else {
                return ApiResponse.error("Transaction type not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{transactionType}")
    public ApiResponse<TransactionTypeEntity> getTransactionTypeByName(@PathVariable String transactionType) {
        try {
            TransactionTypeEntity transactionTypeEntity = transactionTypeService.getTransactionTypeByName(transactionType);
            if (transactionTypeEntity != null) {
                return ApiResponse.success(transactionTypeEntity, "Transaction type found successfully");
            } else {
                return ApiResponse.error("Transaction type not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ApiResponse<List<TransactionTypeEntity>> getAllTransactionTypes() {
        try {
            List<TransactionTypeEntity> transactionTypes = transactionTypeService.getAllTransactionTypes();
            return ApiResponse.success(transactionTypes, "Transaction types retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ApiResponse<TransactionTypeEntity> updateTransactionType(@RequestBody TransactionTypeEntity transactionType) {
        try {
            TransactionTypeEntity updatedTransactionType = transactionTypeService.updateTransactionType(transactionType);
            return ApiResponse.success(updatedTransactionType, "Transaction type updated successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTransactionType(@PathVariable Long id) {
        try {
            boolean deleted = transactionTypeService.deleteTransactionType(id);
            if (deleted) {
                return ApiResponse.success("Transaction type deleted successfully");
            } else {
                return ApiResponse.error("Failed to delete transaction type", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
