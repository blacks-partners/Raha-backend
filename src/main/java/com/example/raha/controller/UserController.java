package com.example.rafa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.rafa.domain.User;
import com.example.rafa.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("")
public class UserController {

    private final UserService service;

    @RequestMapping("")
    public String load() {
        System.out.println(service.load(1));

        return "index";

    }

}
