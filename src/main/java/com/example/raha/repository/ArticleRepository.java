package com.example.raha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.raha.entity.Article;

/**
 * Articleリポジトリ
 * @author T.hosoda
 */
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    public List<Article> findByUserUserId(Integer userId);

    @Modifying
    @Query("""
            UPDATE Article a
            SET a.title = :title,
            a.content = :content,
            a.updatedAt = CURRENT_TIMESTAMP
            WHERE a.articleId = :articleId
            AND a.user.userId = :userId
            """)
    public int updateArticle(String title, String content, Integer articleId, Integer userId);

    public int deleteByArticleIdAndUserUserId(Integer articleId, Integer userId);
}
