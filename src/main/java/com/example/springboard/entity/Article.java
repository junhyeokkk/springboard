package com.example.springboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@JsonIgnoreProperties
@ToString
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title; // 제목 (20자 제한)

    private String writer; // 작성자

    @Column(nullable = false, length = 20)
    private String content; // 내용 (20자 제한)

    @Column(nullable = false)
    private String createdAt; // 작성 시간
}
