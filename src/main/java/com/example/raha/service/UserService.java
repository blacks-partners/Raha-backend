package com.example.raha.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.example.raha.domain.User;
import com.example.raha.form.RegisterUserForm;
import com.example.raha.form.UpdateUserForm;
import com.example.raha.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーに関するサービスクラス
 * 
 * @author T.Kanamaru
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * ユーザー情報詳細の取得。
     * @param id ユーザーID
     * @return user ユーザー
     */
    public User load(Integer id) {
        User user = repository.load(id);
        return user;
    }

    /**
     * ユーザー登録をする。
     * 
     * @param user ユーザー
     * @return id ユーザーID
     */
    public Integer register(RegisterUserForm form){
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        Integer id = repository.insert(form);
        return id;
    }

    /**
     * メールアドレスを持っているユーザーを探す。
     * 
     * @param email メールアドレス
     * @return user ユーザー
     */
    public User findByEmail(String email){
        if(email == null){
            return null;
        }
        User user = repository.findByEmail(email);
        return user;
    }

    /*
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

    /**
     * ユーザーの削除。
     * 
     * @param userId ユーザーID
     */
    public void delete(Integer userId) {
        repository.delete(userId);
    }

    /*
     * ユーザー情報の更新。
     * 
     * @param userId ユーザーID
     * @param form ユーザー更新フォームの内容。
     */
    public void update(Integer userId, UpdateUserForm form) {
        repository.update(userId, form);
    }

}
