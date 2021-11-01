package com.example.blogbackend.services;

import com.example.blogbackend.models.Article;
import com.example.blogbackend.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAll(){
        return this.articleRepository.findAll();
    }

    public Article getArticleById(UUID id){
        return this.articleRepository.findById(id).orElseThrow(()->{
            String msg = String.format("Article with the id of %s not found",id.toString());
            return new RuntimeException(msg);
        });
    }

    public Article addArticle(Article article){
        return this.articleRepository.save(article);
    }

    @Transactional
    public Article editArticle(Article article, UUID id){
        var oldArticle = this.articleRepository.findById(id).orElseThrow(()->{
            String msg = String.format("Article with the id of %s not found",id.toString());
            return new RuntimeException(msg);
        });
        oldArticle.setBody(article.getBody());
        oldArticle.setTitle(article.getTitle());
        return this.articleRepository.save(oldArticle);
    }

    public ResponseEntity<?> deleteArticle(UUID id){
        try{
            this.articleRepository.deleteById(id);
            return ResponseEntity.ok("Article deleted");
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }

}
