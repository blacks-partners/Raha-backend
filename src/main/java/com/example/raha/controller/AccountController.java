package com.example.raha.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.raha.domain.User;
import com.example.raha.error.ConflictException;
import com.example.raha.form.RegisterUserForm;
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
     * @param RegisterUserForm
     * @return ユーザーIDを返す。
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> register(@RequestBody RegisterUserForm form){
        User registeredUser = service.findByEmail(form.getEmail());
        if(registeredUser != null){
            throw new ConflictException("入力されたメールアドレスは既に登録されています");
        }
        Integer id = service.register(form);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
