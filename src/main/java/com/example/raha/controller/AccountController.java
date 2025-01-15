package com.example.raha.controller;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.raha.form.LoginForm;

import lombok.RequiredArgsConstructor;

/**
 * アカウントコントローラー
 * 
 * @author hosodatomoya
 */
@RestController
@RequiredArgsConstructor
public class AccountController {
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
            provider.authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));
            String token = JWT.create().withClaim("username", form.getEmail())
                    .withExpiresAt(OffsetDateTime.now().plusMinutes(60).toInstant()).sign(Algorithm.HMAC256(secret));
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-AUTH-TOKEN", "Bearer " + token);
            return new ResponseEntity<>(headers, HttpStatus.OK);

        } catch (AuthenticationException e) {
            Map<String, String> response = Map.of("message", "メールアドレス又はパスワードが誤っています");
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
