package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;

@Data
@Entity
@Table(name="article_recommend")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
public class ArticleRecoInfoEntity { // 게시물 추천 정보
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ar_seq") private Long arSeq;
    @Column(name = "ar_ai_seq") private Long arAiSeq;
    @Column(name = "ar_mi_seq") private Long arMiSeq;
}
