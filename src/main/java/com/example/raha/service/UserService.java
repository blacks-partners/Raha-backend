package com.example.raha.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.raha.domain.User;
import com.example.raha.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーに関するサービスクラス
 * 
 * @author S.Kanamaru
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * ユーザー情報を取得
     * 
     * @param id ID
     * @return ユーザー情報
     */
    public User load(Integer id) {
        return repository.load(id);

    }

    /**
     * メールアドレスからユーザー情報を取得
     * 
     * @param email メールアドレス
     * @return ユーザー情報
     */
    public User loadByEmail(String email) {
        return repository.loadByEmail(email);
    }

    /**
     * JWTヘッダーを生成
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
