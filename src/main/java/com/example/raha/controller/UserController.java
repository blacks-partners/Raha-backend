package com.example.raha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.raha.domain.User;
import com.example.raha.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Userのコントローラー
 * 
 * @author R.Naka
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
     * ユーザーの削除。
     * 
     * @param userId ユーザーID
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer userId) {
        service.delete(userId);
    }

}
