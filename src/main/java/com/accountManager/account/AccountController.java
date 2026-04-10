package com.accountManager.account;

import com.accountManager.common.ApiResponse;
import com.accountManager.common.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountInterface accountService;

    public AccountController(AccountInterface accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ApiResponse<Account> createAccount(@RequestBody Account account) {
        try {
            Account savedAccount = accountService.saveAccount(account);
            return ApiResponse.success(savedAccount, ApplicationConstants.ACCOUNT_CREATED_SUCCESSFULLY);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<Account> getAccountById(Authentication authentication, @PathVariable Long id) {
        try {
            Account account = accountService.getAccountById(id, authentication);
            if (account != null) {
                return ApiResponse.success(account, ApplicationConstants.ACCOUNT_FOUND_SUCCESSFULLY);
            } else {
                return ApiResponse.error(ApplicationConstants.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{accountName}")
    public ApiResponse<Account> getAccountByName(@PathVariable String accountName) {
        try {
            Account account = accountService.getAccountByName(accountName);
            if (account != null) {
                return ApiResponse.success(account, ApplicationConstants.ACCOUNT_FOUND_SUCCESSFULLY);
            } else {
                return ApiResponse.error(ApplicationConstants.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<java.util.List<Account>> getAllAccounts() {
        try {
            java.util.List<Account> accounts = accountService.getAllAccounts();
            return ApiResponse.success(accounts, ApplicationConstants.ACCOUNTS_RETRIEVED_SUCCESSFULLY);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ApiResponse<Account> updateAccount(@RequestBody Account account) {
        try {
            Account updatedAccount = accountService.updateAccount(account);
            return ApiResponse.success(updatedAccount, ApplicationConstants.ACCOUNT_UPDATED_SUCCESSFULLY);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAccount(@PathVariable Long id) {
        try {
            boolean deleted = accountService.deleteAccount(id);
            if (deleted) {
                return ApiResponse.success(ApplicationConstants.ACCOUNT_DELETED_SUCCESSFULLY);
            } else {
                return ApiResponse.error("Failed to delete account", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
