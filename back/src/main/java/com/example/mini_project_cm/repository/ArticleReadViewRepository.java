package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.ArticleReadViewEntity;
import com.example.mini_project_cm.vo.article.ArticleReadVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleReadViewRepository extends JpaRepository<ArticleReadViewEntity, Integer> {
//전체검색
@Query("SELECT a FROM ArticleReadViewEntity a")
public List<ArticleReadVO> findAll(PageRequest pageRequest);
//작성자로 검색
@Query("SELECT a FROM ArticleReadViewEntity a WHERE a.miNickname like %:keyword%")
public List<ArticleReadVO> searchNickname(@Param("keyword") String keyword, PageRequest pageRequest);
// 제목으로 검색
@Query("SELECT a FROM ArticleReadViewEntity a WHERE a.aiTitle like %:keyword%")
public List<ArticleReadVO> searchTitle(@Param("keyword") String keyword, PageRequest pageRequest);
//내용으로 검색
@Query("SELECT a FROM ArticleReadViewEntity a WHERE a.aiDetail like %:keyword%")
public List<ArticleReadVO> searchDetail(@Param("keyword") String keyword, PageRequest pageRequest);
public ArticleReadViewEntity findByAiSeq(Long aiSeq);
}
