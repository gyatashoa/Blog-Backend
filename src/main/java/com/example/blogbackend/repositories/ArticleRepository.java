package com.example.blogbackend.repositories;

import com.example.blogbackend.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
