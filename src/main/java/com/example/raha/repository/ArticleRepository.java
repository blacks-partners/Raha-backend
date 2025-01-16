package com.example.raha.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.raha.domain.Article;
import com.example.raha.domain.Comment;
import com.example.raha.domain.User;
import com.example.raha.form.ArticleForm;

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
        article.setContent(rs.getString("a_content"));
        article.setCreatedAt(rs.getTimestamp("a_created_at").toLocalDateTime());
        article.setUpdatedAt(rs.getTimestamp("a_updated_at").toLocalDateTime());
        article.setCommentList(null);

        User user = new User();
        user.setUserId(rs.getInt("u_id"));
        user.setName(rs.getString("u_name"));

        article.setUser(user);
        return article;
    };

    private static final ResultSetExtractor<Article> ARTICLE_RESULTSET = (rs) -> {
        Article article = null;
        List<Comment> commentList = new ArrayList<>();

        while (rs.next()) {
            if (article == null) {
                article = new Article();
                article.setArticleId(rs.getInt("a_id"));
                article.setTitle(rs.getString("a_title"));
                article.setContent(rs.getString("a_content"));
                article.setCreatedAt(rs.getTimestamp("a_created_at").toLocalDateTime());
                article.setUpdatedAt(rs.getTimestamp("a_updated_at").toLocalDateTime());

                User articleUser = new User();
                articleUser.setUserId(rs.getInt("u_id"));
                articleUser.setName(rs.getString("u_name"));

                article.setUser(articleUser);

            }

            if (rs.getInt("c_id") != 0) {

                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("c_id"));
                comment.setContent(rs.getString("c_content"));
                comment.setCreatedAt(rs.getTimestamp("c_created_at").toLocalDateTime());
                comment.setUpdatedAt(rs.getTimestamp("c_updated_at").toLocalDateTime());

                User commentUser = new User();
                commentUser.setUserId(rs.getInt("e_id"));
                commentUser.setName(rs.getString("e_name"));

                comment.setUser(commentUser);
                commentList.add(comment);
            }

        }

        if (article == null) {
            return null;
        }

        article.setCommentList(commentList);

        return article;
    };

    /**
     * 記事一覧情報の取得
     * 
     * @return articleList 記事リスト
     */
    public List<Article> findAll() {

        String sql = "SELECT a.id as a_id,a.title as a_title,a.content as a_content,a.created_at as a_created_at,a.updated_at as a_updated_at,u.id as u_id, u.name as u_name FROM articles as a LEFT OUTER JOIN users as u on a.user_id = u.id ORDER BY a.created_at DESC,a.id DESC";

        List<Article> articleList = jdbcTemplate.query(sql, ARTICLE_ROWMAPPER);

        return articleList;
    }

    /**
     * 記事詳細情報の取得
     * 
     * @param articleId 記事ID
     * @return article 記事＋コメント情報
     */
    public Article articleDetails(Integer articleId) {

        String sql = "SELECT a.id as a_id,a.title as a_title,a.content as a_content,a.created_at as a_created_at,a.updated_at as a_updated_at,u.id as u_id, u.name as u_name,c.id as c_id,c.content as c_content,c.created_at as c_created_at,c.updated_at as c_updated_at, e.id as e_id, e.name as e_name FROM articles as a LEFT OUTER JOIN users as u on a.user_id = u.id LEFT OUTER JOIN comments as c on a.id =c.article_id LEFT OUTER JOIN  users as e  on e.id = c.user_id WHERE a.id=:articleId ORDER BY a.created_at DESC,a.id DESC,c.created_at DESC,c.id DESC";

        SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);

        Article article = jdbcTemplate.query(sql, param, ARTICLE_RESULTSET);

        return article;
    }

    /**
     * 投稿された記事内容を登録
     * 
     * @param article 登録する記事内容
     * @return articleId 自動採番されたid
     */
    @SuppressWarnings("null")
    public Integer insert(ArticleForm article) {
        String sql = "INSERT INTO articles (title,content,user_id) VALUES (:title,:content,:userId)";

        SqlParameterSource param = new MapSqlParameterSource().addValue("title", article.getTitle())
                .addValue("content", article.getContent())
                .addValue("userId", article.getUserId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String[] keyColumnName = { "id" };

        jdbcTemplate.update(sql, param, keyHolder, keyColumnName);

        Integer articleId = keyHolder.getKey().intValue();

        return articleId;
    }

    /**
     * 該当の記事削除
     * 
     * @param articleId 記事ID
     */
    public void delete(Integer articleId) {
        String sql = "DELETE FROM articles WHERE id=:id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", articleId);

        jdbcTemplate.update(sql, param);

    }

}
