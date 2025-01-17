package com.example.raha.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.raha.domain.User;
import com.example.raha.error.ConflictException;
import com.example.raha.form.RegisterUserForm;
import com.example.raha.service.UserService;
import lombok.RequiredArgsConstructor;
import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.raha.error.invalidAuthenticationException;
import com.example.raha.form.LoginForm;

/**
 * アカウントコントローラー
 * 
 * @author hosodatomoya
 */
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final UserService service;
    private final DaoAuthenticationProvider provider;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * ログイン
     * 
     * @param form ログインフォーム
     * @return レスポンス
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginForm form) {

        try {
            // ユーザーの認証
            provider.authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));
            User user = service.loadByEmail(form.getEmail());

            // userIdクレーム、60分の有効期限を持つJWTトークンの生成
            String token = JWT.create().withClaim("userId", user.getUserId())
                    .withExpiresAt(OffsetDateTime.now().plusDays(1).toInstant()).sign(Algorithm.HMAC256(secret));
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-AUTH-TOKEN", "Bearer " + token);
            return new ResponseEntity<>(headers, HttpStatus.OK);

        } catch (AuthenticationException e) {
            throw new invalidAuthenticationException("メールアドレス又はパスワードが誤っています");
        }
    }

    /**
     * ユーザー登録をする。
     * @param RegisterUserForm
     * @return ユーザーIDを返す。
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> register(@RequestBody RegisterUserForm form){
        User registeredUser = service.findByEmail(form.getEmail());
        if(registeredUser != null){
            throw new ConflictException("入力されたメールアドレスは既に登録されています");
        }
        Integer userId = service.register(form);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
