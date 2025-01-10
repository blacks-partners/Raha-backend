package com.example.raha.service;

import org.springframework.stereotype.Service;

import com.example.raha.domain.User;
import com.example.raha.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User load(Integer id) {
        return repository.load(id);

    }

}
