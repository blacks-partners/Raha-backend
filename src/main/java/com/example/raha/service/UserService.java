package com.example.raha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    /**
     * ユーザー情報の更新。
     * 
     * @param userId ユーザーID
     * @param form ユーザー更新フォームの内容。
     */
    public void update(Integer userId, UpdateUserForm form) {
        repository.update(userId, form);
    }

}
