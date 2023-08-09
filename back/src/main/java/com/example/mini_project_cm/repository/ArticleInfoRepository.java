package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.ArticleInfoEntity;
import com.example.mini_project_cm.vo.article.ArticleGetVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleInfoRepository extends JpaRepository<ArticleInfoEntity, Long> {
    public ArticleInfoEntity findByAiSeq(Long aiSeq);
    public List<ArticleGetVO> findListByAiSeq(Long seq);
    public List<ArticleGetVO> findByAiMiSeq(Long seq);
}
