package com.example.raha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.raha.domain.User;
import com.example.raha.form.UpdateUserForm;
import com.example.raha.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーに関するコントローラークラス
 * 
 * @author T.Kanamru
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    /**
     * User情報を取得する。
     * 
     * @param userId ユーザーID
     * @return user ユーザー
     */
    @GetMapping("/{userId}")
    public User userDetails(@PathVariable Integer userId) {
        User user = service.load(userId);
        return user;
    }

    /**
     * ユーザー情報の更新。
     * 
     * @param userId ユーザーID
     * @param form ユーザー更新フォームの内容。
     */
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer userId, @RequestBody UpdateUserForm form) {
        service.update(userId, form);
    }

}
