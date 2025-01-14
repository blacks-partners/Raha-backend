package com.example.raha.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.raha.form.LoginForm;
import com.example.raha.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * ログインコントローラー
 * @author tomoyahosoda
 */
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService service;
    private final PasswordEncoder encoder;

    /**
     * ログイン
     * @param form ログインフォーム
     * @return レスポンス
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginForm form) {
        UserDetails userDetails = service.loadUserByUsername(form.getEmail());
        if (userDetails == null || !(encoder.matches(form.getPassword(), userDetails.getPassword()))) {
            Map<String, String> response = Map.of("message", "メールアドレス又はパスワードが誤っています");
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.UNAUTHORIZED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "testToken");

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
