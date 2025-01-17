package com.example.raha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.raha.domain.User;
import com.example.raha.form.RegisterUserForm;
import com.example.raha.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーに関するサービスクラス
 * 
 * @author 金丸天
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ユーザー情報を取得
     * 
     * @param id ID
     * @return ユーザー情報
     */
    public User load(Integer id) {
        User user = repository.load(id);
        return user;

    }

    /**
     * ユーザー登録をする。
     * 
     * @param user
     * @return ユーザーIDを返す。
     */
    public Integer register(RegisterUserForm form){
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        Integer id = repository.insert(form);
        return id;
    }

    /**
     * メールアドレスを持っているユーザーを探す。
     * 
     * @param email
     * @return user
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
}
