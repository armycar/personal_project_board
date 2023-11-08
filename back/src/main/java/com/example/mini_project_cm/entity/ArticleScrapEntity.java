package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@Table(name="article_scrap")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
public class ArticleScrapEntity { // 게시물 스크랩
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "as_seq") private Long asSeq;
    @Column(name = "as_ai_seq") private Long asAiSeq;
    @Column(name = "as_mi_seq") private Long asMiSeq;
}
