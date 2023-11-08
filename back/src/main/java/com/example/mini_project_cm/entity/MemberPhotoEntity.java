package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="member_photo")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberPhotoEntity { // 회원사진
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mp_seq") private Long mpSeq;
    @Column(name = "mp_file_name") private String mpFileName;
    @Column(name = "mp_file_url") private String mpFileUrl;
    @Column(name = "mp_mi_seq") private Long mpMiSeq;

}
