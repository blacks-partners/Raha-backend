package com.example.raha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.raha.domain.User;
import com.example.raha.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class UserController {

    private final UserService service;

    @RequestMapping("")
    public void load() {
        System.out.println(service.load(1));

        // return "index";

    }

}
