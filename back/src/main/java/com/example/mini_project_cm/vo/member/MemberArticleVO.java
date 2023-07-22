package com.example.mini_project_cm.vo.member;

import com.example.mini_project_cm.vo.article.ArticleGetVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class MemberArticleVO {
    private List<ArticleGetVO> article;
    @Schema(description = "성공(true)/실패(false)", example = "true")
    private Boolean status;
    @Schema(description = "메세지", example = "message")
    private String message;
    @Schema(description = "Http Code", example = "HttpStatus.OK")
    private HttpStatus code;
}
