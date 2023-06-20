package com.example.mini_project_cm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="comment_photo")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentPhotoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cp_seq") private Long cpSeq;
    @Column(name = "cp_file_name") private String cpFileName;
    @Column(name = "cp_file_url") private String cpFileUrl;
    @Column(name = "cp_ci_seq") private Long cpCiSeq;

}
