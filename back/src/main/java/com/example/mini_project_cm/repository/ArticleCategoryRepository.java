package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.ArticleCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategoryEntity, Long> {
}
