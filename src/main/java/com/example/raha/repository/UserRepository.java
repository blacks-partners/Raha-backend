package com.example.raha.repository;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.raha.domain.User;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーに関するリポジトリクラス
 * 
 * @author 金丸天
 */
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final NamedParameterJdbcTemplate template;
}
