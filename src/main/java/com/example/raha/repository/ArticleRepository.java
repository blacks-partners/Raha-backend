package com.example.raha.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.raha.domain.Article;
import com.example.raha.domain.User;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーに関するリポジトリークラス
 * 
 * @author 金丸天
 */
@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Article> ARTICLE_ROWMAPPER = (rs, i) -> {
        Article article = new Article();
        article.setArticleId(rs.getInt("a_id"));
        article.setTitle(rs.getString("a_title"));
        article.setContent(rs.getString("a_context"));
        article.setCreatedAt(rs.getTimestamp("a_created_at").toLocalDateTime());
        article.setUpdatedAt(rs.getTimestamp("a_update_at").toLocalDateTime());
        article.setCommentList(null);

        User user = new User();
        user.setUserId(rs.getInt("u_id"));
        user.setName(rs.getString("u_name"));

        article.setUser(user);
        return article;
    };

    /**
     * 記事一覧情報の取得
     * 
     * @return articleList 記事リスト
     */
    public List<Article> findAll() {
        List<Article> articleList = new ArrayList<>();

        String sql = "SELECT a.id as a_id,a.title as a_title,a.context as a_context,a.created_at as a_created_at,a.update_at as a_update_at,u.id as u_id, u.name as u_name FROM articles as a LEFT OUTER JOIN users as u on a.user_id = u.id ORDER BY a.created_at DESC,a.id DESC";

        articleList = jdbcTemplate.query(sql, ARTICLE_ROWMAPPER);

        return articleList;
    }

}
