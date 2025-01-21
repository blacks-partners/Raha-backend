package com.example.raha.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import com.example.raha.domain.Article;
import com.example.raha.form.ArticleForm;

@SpringBootTest
@Transactional
@Sql("TestSql.sql")
@Import(ArticleRepository.class)
public class ArticleRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ArticleRepository articleRepository;

    private final static RowMapper<Integer> MAX_ROW_MAPPER = (rs, i) -> {
        return rs.getInt("max");
    };

    @Test
    void testFindAll() {
        List<Article> articleListTest = articleRepository.findAll();
        assertNotNull(articleListTest);
    }

    @Test
    void testArticleDetails() {
        String sql = "SELECT max(id) FROM articles";

        Integer maxId = jdbcTemplate.queryForObject(sql, MAX_ROW_MAPPER);

        Article articleTest = articleRepository.articleDetails(maxId);
        assertThat(articleTest.getTitle()).isEqualTo("HTMLとCSSで作るWebサイト");

    }

    @Test
    void testInsert() {
        ArticleForm articleForm = new ArticleForm("デモ記事", "デモ内容", 1);

        Integer insertId = articleRepository.insert(articleForm);

        String sql = "SELECT max(id) FROM articles";

        Integer maxId = jdbcTemplate.queryForObject(sql, MAX_ROW_MAPPER);

        assertThat(insertId).isEqualTo(maxId);

    }

    @Test
    void testUserArticleFindAll() {
        Integer userId = 1;
        List<Article> articleListTest = articleRepository.userArticleFindAll(userId);
        assertNotNull(articleListTest);

    }

    // @Test
    // void testDelete() {

    // }

    // @Test
    // void testUpdate() {

    // }

}
