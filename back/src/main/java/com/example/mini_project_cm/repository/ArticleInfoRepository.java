package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.ArticleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleInfoRepository extends JpaRepository<ArticleInfoEntity, Long> {
    public ArticleInfoEntity findByAiSeq(Long aiSeq);
}
