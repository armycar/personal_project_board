package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.CommentInfoEntity;
import com.example.mini_project_cm.vo.comment.CommentGetVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentInfoRepository extends JpaRepository<CommentInfoEntity, Long> {
    public CommentInfoEntity findByCiSeq(Long ciSeq);
    public List<CommentInfoEntity> findAllByCiAiSeq(Long ciAiSeq);
    public List<CommentGetVO> findByCiAiSeq(Long ciAiSeq);
}
