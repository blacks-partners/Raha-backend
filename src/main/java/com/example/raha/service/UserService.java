package com.example.raha.service;

import org.springframework.stereotype.Service;

import com.example.raha.domain.User;
import com.example.raha.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーに関するサービスクラス
 * 
 * @author 金丸天
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
}
