package com.example.raha.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.dto.ArticleDto;
import com.example.raha.dto.ArticleWithCommentsDto;
import com.example.raha.entity.Article;
import com.example.raha.entity.User;
import com.example.raha.form.ArticleForm;
import com.example.raha.repository.ArticleRepository;
import com.example.raha.util.DtoMapper;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

/**
 * 記事に関するサービスクラス
 * 
 * @author S.Kanamaru
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final EntityManager entityManager;
    private final DtoMapper dtoMapper;

    /**
     * 記事一覧情報の取得
     * 
     * @return articleList 記事リスト
     */
    public List<ArticleDto> findAll(Sort sort) {
        List<ArticleDto> articles = dtoMapper.toArticleDtoList(articleRepository.findAll(sort));
        return articles;
    }

    /**
     * 記事詳細情報の取得
     * 
     * @param articleId 記事ID
     * @return article 記事+コメント情報
     */
    public ArticleWithCommentsDto articleDetails(Integer articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);
        if (article == null) {
            return null;
        }
        ArticleWithCommentsDto articleWithCommentsDto = dtoMapper.toArticleWithCommentsDto(article);
        return articleWithCommentsDto;
    }

    /**
     * 投稿された記事内容を登録
     * 
     * @param article 登録する記事内容
     * @return articleId 自動採番されたid
     */
    public Integer insert(ArticleForm articleForm) {
        Article article = new Article();
        BeanUtils.copyProperties(articleForm, article);

        User user = entityManager.getReference(User.class, articleForm.getUserId());
        article.setUser(user);

        Article savedArticle = articleRepository.save(article);

        return savedArticle.getArticleId();
    }

    /**
     * ユーザーの記事一覧を検索する。
     * 
     * @param userId
     * @return ユーザーの記事一覧。
     */
    public List<ArticleDto> userArticleFindAll(Integer userId) {
        List<ArticleDto> articleList = dtoMapper.toArticleDtoList(articleRepository.findByUserUserId(userId));
        return articleList;
    }

    /**
     * 記事内容の更新
     * 
     * @param article   更新する記事情報
     * @param articleId 対象の記事ID
     * @param userId    記事を更新するユーザーID
     */
    public void update(ArticleForm articleForm, Integer articleId, Integer userId) {
        articleRepository.updateArticle(articleForm.getTitle(), articleForm.getContent(), articleId, userId);
    }

    /**
     * 該当の記事削除
     * 
     * @param articleId 記事ID
     */
    public void delete(Integer articleId, Integer userId) {
        articleRepository.deleteByArticleIdAndUserUserId(articleId, userId);
    }

}
