package com.example.raha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.raha.domain.User;
import com.example.raha.form.UserForm;
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
     * @param UserForm
     * @return ユーザーIDを返す。
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer register(@RequestBody UserForm form){
        User registeredUser = service.findByEmail(form.getEmail());
        if(registeredUser != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "入力されたメールアドレスは既に登録されています");
        }
        Integer id = service.register(form);
        return id;
    }
}
