package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.CommentPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPhotoRepository extends JpaRepository<CommentPhotoEntity, Long> {
    public CommentPhotoEntity findByCpCiSeq(Long cpCiSeq);
}
