package com.example.raha.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.raha.dto.ArticleDto;
import com.example.raha.dto.ArticleWithCommentsDto;
import com.example.raha.dto.UserDetailsDto;
import com.example.raha.dto.UserDto;
import com.example.raha.entity.Article;
import com.example.raha.entity.User;

import lombok.RequiredArgsConstructor;

/**
 * EntityをDTOに変換するためのクラス
 * @author T.hosoda
 */
@Component
@RequiredArgsConstructor
public class DtoMapper {
    private final ModelMapper modelMapper;

    public UserDto toUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public UserDetailsDto toUserDetailsDto(User user) {
        return modelMapper.map(user, UserDetailsDto.class);
    }

    public List<ArticleDto> toArticleDtoList(List<Article> articles) {
        return articles.stream().map(article -> modelMapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());
    }

    public ArticleDto toArticleDto(Article article) {
        return modelMapper.map(article, ArticleDto.class);
    }

    public ArticleWithCommentsDto toArticleWithCommentsDto(Article article) {
        return modelMapper.map(article, ArticleWithCommentsDto.class);
    }
}
