package com.example.raha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.raha.domain.User;
import com.example.raha.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * ログインコントローラー
 * @author tomoyahosoda
 */

@RestController
@RequiredArgsConstructor
public class AccountController {
    
    private final UserService service;

    /**
     * ユーザー登録をする。
     * @param user
     * @return ユーザーIDを返す。
     */
    @PostMapping("/register")
    public Integer register(@RequestBody User user){
        User registeredUser = service.findByEmail(user.getEmail());
        if(registeredUser != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "入力されたメールアドレスは既に登録されています");
        }
        return service.register(user);
    }
}
