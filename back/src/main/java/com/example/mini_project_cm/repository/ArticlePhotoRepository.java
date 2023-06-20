package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.ArticlePhotoEntity;
import com.example.mini_project_cm.vo.article.ArticleImgGetVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticlePhotoRepository extends JpaRepository<ArticlePhotoEntity, Long> {
    public List<ArticlePhotoEntity> findByApAiSeq(Long apAiSeq);
    public List<ArticleImgGetVO> findArticleImgGetVOByApAiSeq(Long apAiSeq);
    public ArticlePhotoEntity findByApFileUrl(String apFileUrl);
}
