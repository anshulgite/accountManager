package com.accountManager.auth;

import com.accountManager.auth.refreshToken.CustomeUserDetails;
import com.accountManager.exception.ExceptionMassage;
import org.springframework.security.core.Authentication;

public class AuthorizationUtils {

    public static void validateUserAccess(Authentication authentication, Long resourceUserId) {
        if (authentication != null && resourceUserId != null) {
            CustomeUserDetails customUserDetails = (CustomeUserDetails) authentication.getPrincipal();
            Long userId = customUserDetails.getUserId();
            if (!(resourceUserId.equals(userId))) {
                throw new RuntimeException(ExceptionMassage.UNAUTHORIZED);
            }
        }
    }
}
