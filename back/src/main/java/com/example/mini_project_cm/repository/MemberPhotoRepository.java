package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.MemberPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberPhotoRepository extends JpaRepository<MemberPhotoEntity, Long> {
    public MemberPhotoEntity findByMpMiSeq(Long mpMiSeq);
    public MemberPhotoEntity findByMpFileUrl(String mpFileUrl);
}
