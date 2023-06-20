package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.Date;

@Data @Entity @Table(name = "member_info") @AllArgsConstructor @NoArgsConstructor @Builder
@DynamicInsert
public class MemberInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mi_seq") private Long miSeq;
    @Column(name = "mi_email") private String miEmail;
    @Column(name = "mi_pwd") private String miPwd;
    @Column(name = "mi_nickname") private String miNickName;
    @Column(name = "mi_status") private Integer miStatus;
    @Column(name = "mi_reg_dt") @ColumnDefault("CURRENT_TIMESTAMP") private Date miRegDt;
    @Column(name = "mi_leave_dt") private Date miLeaveDt;

}
