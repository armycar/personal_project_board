package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Data
@Entity
@Table(name="comment_view")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
public class CommentViewEntity { // 댓글 상세보기 뷰
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_seq") private Long       ciSeq;
    @Column(name = "mi_nickname") private String  miNickName;
    @Column(name = "mp_file_url") private String  mpFileUrl;
    @Column(name = "ci_detail") private String  ciDetail;
    @Column(name = "ci_mi_seq") private Long    ciMiSeq;
    @Column(name = "ci_ai_seq") private Long    ciAiSeq;
    @Column(name = "ci_reg_dt") @ColumnDefault("CURRENT_TIMESTAMP") private Date ciRegDt;
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "ci_mi_seq", insertable = false, updatable = false) private MemberInfoEntity memberInfoEntity;
}
