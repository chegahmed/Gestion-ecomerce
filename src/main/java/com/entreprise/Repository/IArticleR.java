package com.entreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.entities.Article;


public interface IArticleR extends JpaRepository<Article, Long> {

}
