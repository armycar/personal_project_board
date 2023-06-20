package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.MyPageViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageViewRepository extends JpaRepository<MyPageViewEntity, Long> {
    public MyPageViewEntity findByMiSeq(Long miSeq);
}
