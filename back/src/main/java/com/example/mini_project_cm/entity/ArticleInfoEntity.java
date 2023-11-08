package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "article_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
public class ArticleInfoEntity { //게시물 정보
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_seq") private Long aiSeq;
    @Column(name = "ai_title") private String aiTitle;
    @Column(name = "ai_detail") private String aiDetail;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ai_reg_dt") @ColumnDefault("CURRENT_TIMESTAMP") private Date aiRegDt;
    @Column(name = "ai_mi_seq")  private Long aiMiSeq;
    @Column(name = "ai_ac_seq")  private Long aiAcSeq;
    @Column(name = "ai_view")  @ColumnDefault("0") private Integer aiView;
}
