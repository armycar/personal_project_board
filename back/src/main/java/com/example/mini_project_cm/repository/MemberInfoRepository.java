package com.example.mini_project_cm.repository;

import com.example.mini_project_cm.entity.MemberInfoEntity;
import com.example.mini_project_cm.vo.member.MemberListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long> {
    public Integer countByMiEmail(String miEmail);
    public Integer countByMiNickName(String miNickName);
    public List<MemberInfoEntity> findByMiStatus(Integer miStatus);
    public MemberInfoEntity findByMiSeq(Long miSeq);
    public MemberInfoEntity findByMiPwd(String miPwd);
    public MemberInfoEntity findByMiEmail(String miEmail);
    public MemberInfoEntity findTop1ByMiEmailAndMiPwd(String miEmail, String miPwd);
    //전체검색
    @Query("SELECT a FROM MemberInfoEntity a")
    public Page<MemberListVO> findAll(PageRequest pageRequest);

    //닉네임검색
    @Query("SELECT a FROM MemberInfoEntity a WHERE a.miNickName like %:keyword%")
    public Page<MemberListVO> searchNickname(@Param("keyword") String keyword, PageRequest pageRequest);
}
