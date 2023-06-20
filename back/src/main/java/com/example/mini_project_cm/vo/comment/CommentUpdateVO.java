package com.example.mini_project_cm.vo.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class CommentUpdateVO {
    @Schema(description = "댓글내용", example = "test")
    private String detail;
}
