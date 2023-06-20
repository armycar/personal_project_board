package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.ArticleInfoEntity;
import com.example.mini_project_cm.entity.ArticleRecoInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRecoRepository extends JpaRepository<ArticleRecoInfoEntity, Long> {
    public ArticleRecoInfoEntity findByArAiSeqAndArMiSeq(Long arAiSeq, Long arMiSeq);
}
