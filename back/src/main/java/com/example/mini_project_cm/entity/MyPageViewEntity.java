package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "mypage_view")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageViewEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mi_seq") private Long miSeq;
    @Column(name = "mi_nickname") private String miNickname;
    @Column(name = "mi_email") private String miEmail;
    @Column(name = "mp_file_url") private String mpFileUrl;
}
