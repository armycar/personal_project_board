package com.example.mini_project_cm.vo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class MemberJoinVO {
    @Schema(description = "이메일", example = "user01@naver.com")
    private String email;
    @Schema(description = "비밀번호", example = "12341234")
    private String pwd;
    @Schema(description = "비밀번호 확인", example = "12341234")
    private String pwdconfirm;
    @Schema(description = "닉네임", example = "user01")
    private String nickname;
}
