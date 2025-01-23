package com.example.raha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.raha.domain.User;
import com.example.raha.error.ConflictException;
import com.example.raha.form.RegisterUserForm;
import com.example.raha.service.UserService;
import lombok.RequiredArgsConstructor;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import com.example.raha.error.invalidAuthenticationException;
import com.example.raha.form.LoginForm;

import com.example.raha.service.SecurityUserService;

/**
 * アカウントコントローラー
 * 
 * @author hosodatomoya
 */
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final UserService userService;
    private final SecurityUserService securityUserService;
    private final DaoAuthenticationProvider provider;

    /**
     * ログイン
     * 
     * @param form ログインフォーム
     * @return レスポンス
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Integer>> login(@RequestBody LoginForm form) {

        try {
            // ユーザーの認証
            provider.authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));
            User user = userService.loadByEmail(form.getEmail());

            // JWTトークンの生成
            HttpHeaders headers = securityUserService.createJwtHeader(user.getUserId());
            Map<String, Integer> response = Map.of("userId", user.getUserId());
            return new ResponseEntity<>(response, headers, HttpStatus.OK);

        } catch (AuthenticationException e) {
            throw new invalidAuthenticationException("メールアドレス又はパスワードが誤っています");
        }
    }

    /**
     * ユーザー登録をする。
     * 
     * @param RegisterUserForm
     * @return ユーザーIDを返す。
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Integer>> register(@RequestBody RegisterUserForm form) {
        User registeredUser = userService.findByEmail(form.getEmail());
        if (registeredUser != null) {
            throw new ConflictException("入力されたメールアドレスは既に登録されています");
        }
        Integer userId = userService.register(form);

        // JWTトークンの生成
        HttpHeaders headers = userService.createJwtHeader(userId);
        Map<String, Integer> response = Map.of("userId", userId);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
