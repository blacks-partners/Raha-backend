package com.example.raha.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.raha.domain.User;
import com.example.raha.repository.UserRepository;

/**
 * SecurityConfigクラスで使用するユーザーサービス
 * 
 * @author hosodatomoya
 */
@Service
@Transactional
public class SecurityUserService implements UserDetailsService {
    private final UserRepository repository;
    private final String secret;

    public SecurityUserService(@Value("${jwt.secret}") String secret, UserRepository repository) {
        this.repository = repository;
        this.secret = secret;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.loadByEmail(email);
        if (user == null) {
            return null;
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    /**
     * JWTヘッダーを生成
     * 
     * @param userId ユーザーID
     * @return JWTトークン付きHttpヘッダー
     */
    public HttpHeaders createJwtHeader(Integer userId) {
        // userIdクレーム、1日の有効期限を持つJWTトークンの生成
        String token = JWT.create().withClaim("userId", userId)
                .withExpiresAt(OffsetDateTime.now().plusDays(1).toInstant()).sign(Algorithm.HMAC256(secret));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AUTH-TOKEN", "Bearer " + token);
        return headers;
    }

}
