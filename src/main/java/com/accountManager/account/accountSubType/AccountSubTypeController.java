package com.accountManager.account.accountSubType;

import com.accountManager.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-sub-types")
public class AccountSubTypeController {

    private final AccountSubTypeInterface accountSubTypeService;

    public AccountSubTypeController(AccountSubTypeInterface accountSubTypeService) {
        this.accountSubTypeService = accountSubTypeService;
    }

    @PostMapping
    public ApiResponse<AccountSubTypeEntity> createAccountSubType(@RequestBody AccountSubTypeEntity accountSubType) {
        try {
            AccountSubTypeEntity savedAccountSubType = accountSubTypeService.saveAccountSubType(accountSubType);
            return ApiResponse.success(savedAccountSubType, "Account sub type created successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<AccountSubTypeEntity> getAccountSubTypeById(@PathVariable Long id) {
        try {
            AccountSubTypeEntity accountSubType = accountSubTypeService.getAccountSubTypeById(id);
            if (accountSubType != null) {
                return ApiResponse.success(accountSubType, "Account sub type found successfully");
            } else {
                return ApiResponse.error("Account sub type not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{accountSubType}")
    public ApiResponse<AccountSubTypeEntity> getAccountSubTypeByName(@PathVariable String accountSubType) {
        try {
            AccountSubTypeEntity accountSubTypeEntity = accountSubTypeService.getAccountSubTypeByName(accountSubType);
            if (accountSubTypeEntity != null) {
                return ApiResponse.success(accountSubTypeEntity, "Account sub type found successfully");
            } else {
                return ApiResponse.error("Account sub type not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ApiResponse<List<AccountSubTypeEntity>> getAllAccountSubTypes() {
        try {
            List<AccountSubTypeEntity> accountSubTypes = accountSubTypeService.getAllAccountSubTypes();
            return ApiResponse.success(accountSubTypes, "Account sub types retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-account-type/{accountTypeId}")
    public ApiResponse<List<AccountSubTypeEntity>> getAccountSubTypesByAccountTypeId(@PathVariable Long accountTypeId) {
        try {
            List<AccountSubTypeEntity> accountSubTypes = accountSubTypeService.getAccountSubTypesByAccountTypeId(accountTypeId);
            return ApiResponse.success(accountSubTypes, "Account sub types retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ApiResponse<AccountSubTypeEntity> updateAccountSubType(@RequestBody AccountSubTypeEntity accountSubType) {
        try {
            AccountSubTypeEntity updatedAccountSubType = accountSubTypeService.updateAccountSubType(accountSubType);
            return ApiResponse.success(updatedAccountSubType, "Account sub type updated successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAccountSubType(@PathVariable Long id) {
        try {
            boolean deleted = accountSubTypeService.deleteAccountSubType(id);
            if (deleted) {
                return ApiResponse.success("Account sub type deleted successfully");
            } else {
                return ApiResponse.error("Failed to delete account sub type", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
