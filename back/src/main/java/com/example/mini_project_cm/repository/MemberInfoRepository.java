package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.MemberInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long> {
    public Integer countByMiEmail(String miEmail);
    public Integer countByMiNickName(String miNickName);
    public List<MemberInfoEntity> findByMiStatus(Integer miStatus);
    public MemberInfoEntity findByMiSeq(Long miSeq);
    public MemberInfoEntity findByMiPwd(String miPwd);
    public MemberInfoEntity findByMiEmail(String miEmail);
    public MemberInfoEntity findTop1ByMiEmailAndMiPwd(String miEmail, String miPwd);
}
