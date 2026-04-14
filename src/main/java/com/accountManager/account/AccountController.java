package com.accountManager.account;

import com.accountManager.account.accountSubType.AccountSubTypeInterface;
import com.accountManager.account.accountType.AccountTypeInterface;
import com.accountManager.common.ApiResponse;
import com.accountManager.common.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {

    private final AccountInterface accountService;
    private final AccountTypeInterface accountTypeService;
    private final AccountSubTypeInterface accountSubTypeService;

    public AccountController(AccountInterface accountService, AccountTypeInterface accountTypeService, AccountSubTypeInterface accountSubTypeService) {
        this.accountService = accountService;
        this.accountTypeService = accountTypeService;
        this.accountSubTypeService = accountSubTypeService;
    }

    @PostMapping
    public ApiResponse<Account> createAccount(@RequestBody Account account) {
        try {
            log.info("Creating account: {}", account);
            if (account == null) {
                return ApiResponse.error(ApplicationConstants.INVALID_ACCOUNT_DATA, HttpStatus.BAD_REQUEST);
            }
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
                log.info("Account found successfully");
                account.setAccountType(accountTypeService.getAccountTypeById(account.getAccountTypeId()).getAccountType());
                account.setAccountSubType(accountSubTypeService.getAccountSubTypeById(account.getAccountSubTypeId()).getAccountSubType());
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
                log.info("Account found successfully");
                account.setAccountType(accountTypeService.getAccountTypeById(account.getAccountTypeId()).getAccountType());
                account.setAccountSubType(accountSubTypeService.getAccountSubTypeById(account.getAccountSubTypeId()).getAccountSubType());
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
            accounts.forEach(account -> {
                account.setAccountType(accountTypeService.getAccountTypeById(account.getAccountTypeId()).getAccountType());
                account.setAccountSubType(accountSubTypeService.getAccountSubTypeById(account.getAccountSubTypeId()).getAccountSubType());
            });
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
