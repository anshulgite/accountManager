package com.accountManager.account.accountType;

import com.accountManager.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account-types")
public class AccountTypeController {

    private final AccountTypeInterface accountTypeService;

    public AccountTypeController(AccountTypeInterface accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @PostMapping
    public ApiResponse<AccountTypeEntity> createAccountType(@RequestBody AccountTypeEntity accountType) {
        try {
            AccountTypeEntity savedAccountType = accountTypeService.saveAccountType(accountType);
            return ApiResponse.success(savedAccountType, "Account type created successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<AccountTypeEntity> getAccountTypeById(@PathVariable Long id) {
        try {
            AccountTypeEntity accountType = accountTypeService.getAccountTypeById(id);
            if (accountType != null) {
                return ApiResponse.success(accountType, "Account type found successfully");
            } else {
                return ApiResponse.error("Account type not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{accountType}")
    public ApiResponse<AccountTypeEntity> getAccountTypeByName(@PathVariable String accountType) {
        try {
            AccountTypeEntity accountTypeEntity = accountTypeService.getAccountTypeByName(accountType);
            if (accountTypeEntity != null) {
                return ApiResponse.success(accountTypeEntity, "Account type found successfully");
            } else {
                return ApiResponse.error("Account type not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ApiResponse<java.util.List<AccountTypeEntity>> getAllAccountTypes() {
        try {
            java.util.List<AccountTypeEntity> accountTypes = accountTypeService.getAllAccountTypes();
            return ApiResponse.success(accountTypes, "Account types retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ApiResponse<AccountTypeEntity> updateAccountType(@RequestBody AccountTypeEntity accountType) {
        try {
            AccountTypeEntity updatedAccountType = accountTypeService.updateAccountType(accountType);
            return ApiResponse.success(updatedAccountType, "Account type updated successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAccountType(@PathVariable Long id) {
        try {
            boolean deleted = accountTypeService.deleteAccountType(id);
            if (deleted) {
                return ApiResponse.success("Account type deleted successfully");
            } else {
                return ApiResponse.error("Failed to delete account type", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
