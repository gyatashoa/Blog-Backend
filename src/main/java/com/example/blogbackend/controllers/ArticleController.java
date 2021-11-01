package com.example.blogbackend.controllers;


import com.example.blogbackend.models.Article;
import com.example.blogbackend.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public List<Article> getAllArticles(){
        return this.articleService.getAll();
    }

    @PostMapping
    public Article addArticle(@RequestBody Article article,@RequestHeader("Authorization") String jwt){
        return this.articleService.addArticle(article,jwt.substring(7));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        try{
            return ResponseEntity.ok().body(this.articleService.getArticleById(id));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id){
        return this.articleService.deleteArticle(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@RequestBody Article article,@PathVariable UUID id){
        return ResponseEntity.ok().body(this.articleService.editArticle(article,id));
    }

}
