package com.example.raha.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
