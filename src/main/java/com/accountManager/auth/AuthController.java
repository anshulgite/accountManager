package com.accountManager.auth;

import com.accountManager.auth.refreshToken.CustomeUserDetails;
import com.accountManager.auth.refreshToken.RefreshToken;
import com.accountManager.auth.refreshToken.RefreshTokenService;
import com.accountManager.common.ApiResponse;
import com.accountManager.common.Encryption;
import com.accountManager.user.UserEntity;
import com.accountManager.user.UserInterface;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final RefreshTokenService refreshTokenService;

    private final UserInterface userService;

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,RefreshTokenService refreshTokenService,UserInterface userService,RedisTemplate<String, String> redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService=refreshTokenService;
        this.userService = userService;
        this.redisTemplate =redisTemplate;
    }


    @PostMapping("/register")
    public ApiResponse<UserEntity> saveUser(@RequestBody UserEntity user) {
        try {
            user.setUserRole("ROLE_USER");
            UserEntity userEntity = userService.saveUser(user);
            return ApiResponse.success(userEntity, "User saved successfully");
        }catch (Exception e)
        {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest request) throws NoSuchAlgorithmException {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

       CustomeUserDetails userDetails = (CustomeUserDetails) authenticate.getPrincipal();

        assert userDetails != null;
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        Long userId = userDetails.getUserId();
        String email = userDetails.getEmail();

        String accessToken = jwtUtil.generateToken(request.getUsername(),role,userId,email);
        String refreshToken = jwtUtil.generateRefreshToken(request.getUsername(),role);

        RefreshToken rt = new RefreshToken();
        rt.setToken(Encryption.hashToken(refreshToken));
        rt.setUsername(request.getUsername());
        rt.setExpiryDate(LocalDateTime.now().plusDays(7));
        rt.setRevoked(false);
        refreshTokenService.save(rt);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);


        return response;
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> request) throws NoSuchAlgorithmException {

        String refreshToken = request.get("refreshToken");

        String username = jwtUtil.extractUsername(refreshToken);
        Claims claims = jwtUtil.getClaims(refreshToken);
        String role = claims.get("role", String.class);
        String email = claims.get("email", String.class);
        Long userId = claims.get("userId", Long.class);
        if (jwtUtil.validateRefreshToken(refreshToken)) {

            RefreshToken byToken = refreshTokenService.findByToken(Encryption.hashToken(refreshToken)).orElseThrow(() -> new RuntimeException("Token not found"));

            String newAccessToken = jwtUtil.generateToken(username,role,userId,email);
            String newRefreshToken = jwtUtil.generateRefreshToken(username,role);

            byToken.setToken(Encryption.hashToken(newRefreshToken));
            refreshTokenService.save(byToken);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            response.put("refreshToken", newRefreshToken);

            return response;
        }

        throw new RuntimeException("Invalid Refresh Token");
    }

    @PostMapping("/logout")
    public String logout(@RequestBody Map<String, String> request, HttpServletRequest httpServerRequest) {

        String refreshToken = request.get("refreshToken");
        if(jwtUtil.validateRefreshToken(refreshToken)) {
            try {
                RefreshToken rt = refreshTokenService.findByToken(Encryption.hashToken(refreshToken))
                        .orElseThrow(() -> new RuntimeException("Token not found"));
                rt.setRevoked(true);
                refreshTokenService.save(rt);
            }catch (NoSuchAlgorithmException noSuchAlgorithmException){
                throw new RuntimeException("no such algorithm");
            }

        }
        blackListAccessToken(httpServerRequest);

        return "Logged out successfully";
    }

    @PostMapping("/logoutAll")
    public String logoutAll(@RequestBody Map<String, String> request, HttpServletRequest httpServerRequest) {

       String refreshToken = request.get("refreshToken");
       if(jwtUtil.validateRefreshToken(refreshToken)) {
           try {
           RefreshToken rt = refreshTokenService.findByToken(Encryption.hashToken(refreshToken))
                   .orElseThrow(() -> new RuntimeException("Token not found"));
           String username = jwtUtil.extractUsername(refreshToken);
           List<RefreshToken> byUsername = refreshTokenService.findByUsername(username);
           byUsername.forEach(token -> token.setRevoked(true));
           refreshTokenService.saveAll(byUsername);
           }catch (NoSuchAlgorithmException noSuchAlgorithmException){
               throw new RuntimeException("no such algorithm");
           }

       }

       blackListAccessToken(httpServerRequest);

       return "Logged out successfully";
   }

    private void blackListAccessToken(HttpServletRequest httpServerRequest) {
        String token = httpServerRequest.getHeader("Authorization").substring(7);
        long remainingExpiry = jwtUtil.getRemainingExpiry(token);

        redisTemplate.opsForValue().set( "blacklist:" + token,
                "true",
                remainingExpiry,
                TimeUnit.MILLISECONDS);
    }
}
