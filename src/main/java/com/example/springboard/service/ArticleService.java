package com.example.springboard.service;

import com.example.springboard.entity.Article;
import com.example.springboard.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public Page<Article> getPosts(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Article getPost(Long id) {
        System.out.println("아이디" + id);
        Optional<Article> article = articleRepository.findById(id);

        if (article.isPresent()) {
            Article article1 = article.get();
            return article1;
        } else{
            System.out.println("없음");
            return null;
        }
    }

    public void savePost(Article article) {
        article.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        articleRepository.save(article);
    }

    public void deletePost(Long id) {
        articleRepository.deleteById(id);
    }
}
