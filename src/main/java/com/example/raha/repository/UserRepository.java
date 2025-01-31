package com.example.raha.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.raha.entity.User;



/**
 * ユーザーに関するリポジトリクラス
 *
 * @author S.Kanamaru
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findUserByEmail(String email);
}
