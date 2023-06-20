package com.example.mini_project_cm.vo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class PasswordVO {
    @Schema(description = "기존 회원비밀번호", example = "12341234")
    private String oldpwd;
    @Schema(description = "새로운 회원비밀번호", example = "92449244")
    private String pwd;
    @Schema(description = "새로운 비밀번호확인", example = "92449244")
    private String pwdconfirm;

}
