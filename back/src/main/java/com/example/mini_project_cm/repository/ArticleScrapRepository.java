package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.ArticleRecoInfoEntity;
import com.example.mini_project_cm.entity.ArticleScrapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleScrapRepository extends JpaRepository<ArticleScrapEntity, Long> {
    public ArticleScrapEntity findByAsAiSeqAndAsMiSeq(Long asAiSeq, Long asMiSeq);
    public List<ArticleScrapEntity> findByAsMiSeq(Long seq);
}
