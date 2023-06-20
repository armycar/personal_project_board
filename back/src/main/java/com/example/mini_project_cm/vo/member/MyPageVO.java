package com.example.mini_project_cm.vo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class MyPageVO {
    private String url;
    private String nickname;
    private String email;
    private Long seq;

    @Schema(description = "성공(true)/실패(false)", example = "true")
    private Boolean status;
    @Schema(description = "메세지", example = "message")
    private String message;
    @Schema(description = "Http Code", example = "HttpStatus.OK")
    private HttpStatus code;

}
