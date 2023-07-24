package com.example.mini_project_cm.vo.article;

import com.example.mini_project_cm.vo.comment.CommentGetVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ArticleDetailVO {
    private String detail;
    private Integer ccount; //댓글수
    private Date regdt;
    private String category;
    private Integer vcount; //조회수
    private String title;
    private String nickname;
    private Long aiSeq;
    private Long miSeq;
    private Integer lcount; //좋아요수
    private String url;

    private List<ArticleImgGetVO> img;
    private List<CommentGetVO> comment;
    @Schema(description = "성공(true)/실패(false)", example = "true")
    private Boolean status;
    @Schema(description = "메세지", example = "message")
    private String message;
    @Schema(description = "Http Code", example = "HttpStatus.OK")
    private HttpStatus code;
}
