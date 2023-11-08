package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "article_read_view")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleReadViewEntity { // 게시물 상세보기 뷰
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_seq") private Long aiSeq;
    @Column(name = "ai_title") private String aiTitle;
    @Column(name = "ai_detail") private String aiDetail;
    @Column(name = "ai_reg_dt") private Date aiRegDt;
    @Column(name = "ai_mi_seq") private Long aiMiSeq;
    @Column(name = "mi_nickname") private String miNickname;
    @Column(name = "ac_name") private String acName;
    @Column(name = "ai_view") private Integer   aiView;
    @Column(name = "comment") private Integer   comment;
    @Column(name = "recommend") private Integer recommend;
    @Column(name = "mp_file_url") private String mpFileUrl;
}
