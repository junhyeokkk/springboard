package com.example.springboard.dto;

import com.example.springboard.entity.Article;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class ArticleDto {
    private int number;
    private String title;
    private String content;
    private String writer;
    private String createdAt;
    private Long id;

    public ArticleDto(int number, Article article) {
        this.number = number;
        this.title = article.getTitle();
        this.content = article.getContent();
        this.writer = article.getWriter();
        this.createdAt = article.getCreatedAt();
        this.id = article.getId();
    }

    // Getters and setters (Lombok의 @Data 사용 가능)
}

