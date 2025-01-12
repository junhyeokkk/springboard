package com.example.springboard.controller;

import com.example.springboard.entity.Article;
import com.example.springboard.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 글 목록 보기
    @GetMapping(value = {"/articles", "/"})
    public String listArticles(@RequestParam(defaultValue = "1") int page, Model model) {
        // Spring PageRequest는 0부터 시작하므로 사용자의 입력에서 1을 뺍니다.
        int currentPage = page - 1;

        Pageable pageable = PageRequest.of(currentPage, 5, Sort.by("createdAt").descending()); // 최신순 정렬
        Page<Article> articlePage = articleService.getPosts(pageable);

        // 글 번호 계산 (현재 페이지 기준으로 번호 매기기)
        int startIndex = currentPage * pageable.getPageSize();
        List<Map<String, Object>> articlesWithNumber = new ArrayList<>();
        for (int i = 0; i < articlePage.getContent().size(); i++) {
            Article article = articlePage.getContent().get(i);

            // 글 데이터와 번호 추가
            Map<String, Object> articleMap = new HashMap<>();
            articleMap.put("number", startIndex + i + 1); // 번호 (1부터 시작)
            articleMap.put("title", article.getTitle());
            articleMap.put("content", article.getContent());
            articleMap.put("writer", article.getWriter());
            articleMap.put("createdAt", article.getCreatedAt());
            articleMap.put("id", article.getId());
            articlesWithNumber.add(articleMap);
        }

        // 페이지 번호 리스트 생성 (현재 페이지 여부 포함)
        int totalPages = articlePage.getTotalPages();
        List<Map<String, ? extends Serializable>> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .mapToObj(i -> Map.of(
                        "number", i,
                        "isCurrent", i == page // 현재 페이지 여부 추가
                ))
                .collect(Collectors.toList());

        // 이전/다음 페이지 값 제한
        int previousPage = (currentPage > 0) ? page - 1 : 1;
        int nextPage = (currentPage < totalPages - 1) ? page + 1 : totalPages;

        model.addAttribute("articles", articlesWithNumber);
        model.addAttribute("currentPage", page); // 1부터 시작하는 페이지 번호 전달
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumbers", pageNumbers); // 현재 페이지 여부 포함
        model.addAttribute("hasPrevious", currentPage > 0);
        model.addAttribute("hasNext", currentPage < totalPages - 1);
        model.addAttribute("previousPage", previousPage); // 범위 내의 이전 페이지
        model.addAttribute("nextPage", nextPage);         // 범위 내의 다음 페이지

        return "article-list";
    }


    // 글 작성 폼
    @GetMapping("/articles/new")
    public String showCreateForm(Model model) {
        model.addAttribute("article", new Article());
        return "article-write";
    }

    // 글 작성
    @PostMapping("/articles")
    public String createArticle(@ModelAttribute Article article) {
        articleService.savePost(article);
        return "redirect:/articles";
    }

    // 글 수정 폼
    @GetMapping("/articles/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {

        System.out.println("입성?");
        Article article = articleService.getPost(id);
        model.addAttribute("article", article);
        return "article-edit";
    }

    // 글 수정
    @PostMapping("/articles/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute Article article) {
        article.setId(id);
        articleService.savePost(article);
        return "redirect:/articles";
    }

    // 글 삭제
    @GetMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id) {
        articleService.deletePost(id);
        return "redirect:/articles";
    }
}

