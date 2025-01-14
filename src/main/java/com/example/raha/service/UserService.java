package com.example.raha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.raha.domain.User;
import com.example.raha.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーのServiceクラス。
 * @author nakaryunosuke
 */

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * User情報を取り出す。
     * @param id
     * @return User
     */
    public User load(Integer id) {
        return repository.load(id);

    }

    /**
     * ユーザー登録をする。
     * @param user
     * @return ユーザーIDを返す。
     */
    public Integer register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.insert(user);
        return user.getUserId();
    }

    /**
     * メールアドレスを持っているユーザーを探す。
     * @param email
     * @return user
     */
    public User findByEmail(@PathVariable String email){
        try {
            return repository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

}
