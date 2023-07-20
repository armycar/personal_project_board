package com.example.mini_project_cm.api;

import com.example.mini_project_cm.service.ArticleService;
import com.example.mini_project_cm.vo.BasicResponseVO;
import com.example.mini_project_cm.vo.article.ArticleDetailVO;
import com.example.mini_project_cm.vo.article.ArticleReadVO;
import com.example.mini_project_cm.vo.article.ArticleUpdateVO;
import com.example.mini_project_cm.vo.article.ArticleWriteVO;
import com.example.mini_project_cm.vo.comment.CommentUpdateVO;
import com.example.mini_project_cm.vo.comment.CommentWriteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시글관리", description = "게시글관리 CRUD")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleAPIController {
    private final ArticleService aService;

    @Operation(summary = "게시글 쓰기", description = "사용자로부터 데이터를 받아 게시글을 작성합니다")
    @PutMapping(value = "/article/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponseVO> articleWrite(@ModelAttribute ArticleWriteVO data,
                                                        @Parameter(description = "회원번호", example = "9") Long seq,
                                                        @Parameter(description = "카테고리번호", example = "1") Long cateSeq) {
        return new ResponseEntity<>(aService.writeArticle(data, seq, cateSeq), HttpStatus.OK);
    }
    @Operation(summary = "게시글 전체조회", description = "게시글 리스트와 페이지 정보를 보여줌")
    @GetMapping("/article/read/{type}")
    public ResponseEntity<List<ArticleReadVO>> searchArticle(
           @Parameter(description = "검색타입 = all(전체) / author(글쓴이) / title(제목) / detail(내용)", example = "all") @PathVariable String type,
           @RequestParam(required = false, value = "keyword") String keyword,
           @RequestParam @Nullable Integer page,
           @RequestParam @Nullable Integer size
    )
    {
        List<ArticleReadVO> response = aService.readArticle(type, keyword, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "게시글 상세조회", description = "사용자에게 게시글 번호를 받아서 게시글의 상세정보를 조회한다")
    @GetMapping("/article/detail")
    public ResponseEntity<ArticleDetailVO> detailArticle(Long seq) {
        return new ResponseEntity<>(aService.detailArticle(seq),HttpStatus.OK);
    }
    @Operation(summary = "게시글 수정", description = "사용자에게 회원번호와 데이터를 받아 게시글을 수정함")
    @PatchMapping(value = "/article/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<BasicResponseVO> articleUpdate(@ModelAttribute ArticleUpdateVO data,
                                                         @Parameter(description = "회원번호", example = "9") @RequestParam Long miSeq,
                                                         @Parameter(description = "게시글번호", example = "2") @RequestParam Long aiSeq) {
        return new ResponseEntity<>(aService.updateArticle(data, miSeq, aiSeq),HttpStatus.OK);
    }
    @Operation(summary = "게시글 삭제", description = "사용자에게 회원번호와 게시글번호를 받아 게시글을 삭제함")
    @DeleteMapping("/article/delete")
    public ResponseEntity<BasicResponseVO> articleDelete(@RequestParam
                                                             @Parameter(description = "회원번호", example = "9")Long miSeq,
                                                         @Parameter(description = "게시글번호", example = "1") @RequestParam Long aiSeq) {
        return new ResponseEntity<>(aService.deleteArticle(miSeq, aiSeq),HttpStatus.OK);
    }
    @Operation(summary = "댓글 쓰기", description = "사용자로부터 데이터를 받아 댓글을 작성합니다")
    @PutMapping(value = "/comment/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponseVO> commentWrite(@ModelAttribute CommentWriteVO data,
                                                        @Parameter(description = "회원번호", example = "9") Long miSeq,
                                                        @Parameter(description = "게시글번호", example = "1") Long aiSeq) {
        return new ResponseEntity<>(aService.writeComment(data, miSeq, aiSeq), HttpStatus.OK);
    }
    @Operation(summary = "댓글 수정", description = "사용자에게 회원번호와 데이터를 받아 댓글을 수정함")
    @PatchMapping(value = "/comment/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponseVO> commentUpdate(@ModelAttribute CommentUpdateVO data,
                                                         @Parameter(description = "회원번호", example = "9") @RequestParam Long miSeq,
                                                         @Parameter(description = "댓글번호", example = "2") @RequestParam Long ciSeq) {
        return new ResponseEntity<>(aService.updateComment(data, miSeq, ciSeq),HttpStatus.OK);
    }
    @Operation(summary = "댓글 삭제", description = "사용자에게 회원번호와 댓글번호를 받아 댓글을 삭제함")
    @DeleteMapping("/comment/delete")
    public ResponseEntity<BasicResponseVO> commentDelete(@RequestParam
                                                         @Parameter(description = "회원번호", example = "9")Long miSeq,
                                                         @Parameter(description = "댓글번호", example = "1")Long ciSeq) {
        return new ResponseEntity<>(aService.deleteComment(miSeq, ciSeq),HttpStatus.OK);
    }
    @Operation(summary = "게시글 좋아요", description = "사용자에게 회원번호와 게시글번호를 받아 좋아요를 추가함")
    @PostMapping("/article/recommend")
    public ResponseEntity<BasicResponseVO> articleRecommend(@RequestParam
                                                            @Parameter(description = "회원번호", example = "9")Long miSeq,
                                                            @Parameter(description = "게시글번호", example = "2") Long aiSeq){
        return new ResponseEntity<>(aService.recommendArticle(miSeq, aiSeq),HttpStatus.OK);
    }
}
