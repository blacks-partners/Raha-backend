package com.example.raha.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.raha.dto.UserDetailsDto;
import com.example.raha.entity.User;
import com.example.raha.form.RegisterUserForm;
import com.example.raha.form.UpdateUserForm;
import com.example.raha.repository.UserRepository;
import com.example.raha.util.DtoMapper;

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
    private final DtoMapper dtoMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * ユーザー情報詳細の取得。
     *
     * @param id ユーザーID
     * @return user ユーザー
     */
    public UserDetailsDto load(Integer id) {
        UserDetailsDto user = dtoMapper.toUserDetailsDto(repository.findById(id).orElse(null));
        return user;
    }

    /**
     * ユーザー登録をする。
     *
     * @param user ユーザー
     * @return id ユーザーID
     */
    public Integer register(RegisterUserForm form) {
        User user = new User();
        BeanUtils.copyProperties(form, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = repository.save(user);
        return savedUser.getUserId();
    }

    /**
     * メールアドレスを持っているユーザーを探す。
     *
     * @param email メールアドレス
     * @return user ユーザー
     */
    public UserDetailsDto findByEmail(String email) {
        User user = repository.findUserByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }
        UserDetailsDto userDetailsDto = dtoMapper.toUserDetailsDto(user);
        return userDetailsDto;
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

    /**
     * ユーザーの削除。
     *
     * @param userId ユーザーID
     */
    public void delete(Integer userId) {
        repository.deleteById(userId);
    }

    /*
     * ユーザー情報の更新。
     *
     * @param userId ユーザーID
     *
     * @param form ユーザー更新フォームの内容。
     */
    public void update(Integer userId, UpdateUserForm form) {
        User user = repository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }
        BeanUtils.copyProperties(form, user);
        repository.save(user);
    }

}
