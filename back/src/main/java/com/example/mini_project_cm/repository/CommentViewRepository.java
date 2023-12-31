package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.CommentInfoEntity;
import com.example.mini_project_cm.entity.CommentViewEntity;
import com.example.mini_project_cm.vo.comment.CommentGetVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentViewRepository extends JpaRepository<CommentViewEntity, Long> {
    public List<CommentGetVO> findByCiAiSeq(Long ciAiSeq);
    public List<CommentGetVO> findByCiMiSeq(Long seq);
}
