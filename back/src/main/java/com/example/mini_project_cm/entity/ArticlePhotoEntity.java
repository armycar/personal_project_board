package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "article_photo")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlePhotoEntity { // 게시물 사진
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ap_seq") private Long apSeq;
    @Column(name = "ap_file_name") private String apFileName;
    @Column(name = "ap_file_url") private String apFileUrl;
    @Column(name = "ap_ai_seq") private Long apAiSeq;
}
