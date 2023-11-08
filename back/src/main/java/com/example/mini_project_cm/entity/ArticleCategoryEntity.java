package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "article_category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleCategoryEntity { // 게시물 카테고리
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ac_seq") private Long acSeq;
    @Column(name = "ac_name") private String acName;
}
