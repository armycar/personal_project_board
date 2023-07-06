package com.example.mini_project_cm.service;

import com.example.mini_project_cm.entity.*;
import com.example.mini_project_cm.repository.*;
import com.example.mini_project_cm.vo.BasicResponseVO;
import com.example.mini_project_cm.vo.article.*;
import com.example.mini_project_cm.vo.comment.CommentGetVO;
import com.example.mini_project_cm.vo.comment.CommentUpdateVO;
import com.example.mini_project_cm.vo.comment.CommentWriteVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final MemberInfoRepository miRepo;
    private final ArticleInfoRepository aiRepo;
    private final ArticlePhotoRepository apRepo;
    private final ArticleReadViewRepository rvRepo;
    private final CommentPhotoRepository cpRepo;
    private final CommentInfoRepository ciRepo;
    private final ArticleRecoRepository arRepo;

    @Value("/cm_files/article")
    String article_image_path;
    @Value("/cm_files/comment")
    String comment_image_path;

    //게시글 등록
    public BasicResponseVO writeArticle(ArticleWriteVO data, Long seq, Long cateSeq) {
        BasicResponseVO response = new BasicResponseVO();
        MemberInfoEntity member = miRepo.findByMiSeq(seq);
        if (member == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 회원이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (data.getTitle() == null || data.getTitle() == "") {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("제목을 입력해주세요")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (data.getDetail() == null || data.getDetail() == "") {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("내용을 입력해주세요")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else {
            ArticleInfoEntity article = ArticleInfoEntity.builder()
                    .aiTitle(data.getTitle())
                    .aiDetail(data.getDetail())
                    .aiMiSeq(member.getMiSeq())
                    .aiAcSeq(cateSeq)
                    .build();
            aiRepo.save(article);

            if(data.getImg()!=null) {
            for (int i = 0; i < data.getImg().size(); i++) {
                String originalFileName = data.getImg().get(i).getOriginalFilename();
                String[] split = originalFileName.split("\\.");
                String ext = split[split.length - 1];

                String newUrl = "article_" + member.getMiSeq() + LocalDateTime.now().getNano();
                String saveFileName = "article_" + member.getMiSeq() + LocalDateTime.now().getNano() + "." + ext;

                Path folderLocation = Paths.get(article_image_path);
                Path targetFile = folderLocation.resolve(saveFileName);

                try {
                    Files.copy(data.getImg().get(i).getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    response = BasicResponseVO.builder()
                            .status(false)
                            .message("파일전송에 실패했습니다")
                            .code(HttpStatus.BAD_REQUEST)
                            .build();
                }

                ArticlePhotoEntity photoEntity = ArticlePhotoEntity.builder()
                        .apAiSeq(article.getAiSeq())
                        .apFileName(saveFileName)
                        .apFileUrl(newUrl)
                        .build();

                apRepo.save(photoEntity);
            }
            }

            response = BasicResponseVO.builder()
                    .status(true)
                    .message("게시글 등록이 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();
        }

        return response;
    }

    //게시글 전체 조회
    public List<ArticleReadVO> readArticle(String type, String keyword, Integer page, Integer size) {
        List<ArticleReadVO> response = null;
        if (page == null) page = 0;
        if (size == null) size = 10;
        PageRequest pageRequest = PageRequest.of(page , size, Sort.by("aiRegDt").descending());

        if (type.equals("all")) {
            response = rvRepo.findAll(pageRequest);
        } else if (type.equals("author")) {
            response = rvRepo.searchNickname(keyword, pageRequest);
        } else if (type.equals("title")) {
            response = rvRepo.searchTitle(keyword, pageRequest);
        } else if (type.equals("detail")) {
            response = rvRepo.searchDetail(keyword, pageRequest);
        }
        return response;
    }

    //게시글 상세조회
    public ArticleDetailVO detailArticle(Long seq) {
        ArticleDetailVO response = null;
        ArticleReadViewEntity detail = rvRepo.findByAiSeq(seq);
        ArticleInfoEntity article = aiRepo.findByAiSeq(seq);
        List<CommentGetVO> comment = ciRepo.findByCiAiSeq(seq);
        List<ArticleImgGetVO> img = apRepo.findArticleImgGetVOByApAiSeq(seq);

        if(detail == null) {
            response = ArticleDetailVO.builder()
                    .status(false)
                    .message("존재하지 않는 게시글입니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else {
            Integer count = article.getAiView() +1; //실행될때마다 조회수 증가
            article.setAiView(count);
            aiRepo.save(article);
            response = ArticleDetailVO.builder()
                    .detail(detail.getAiDetail())
                    .ccount(detail.getComment())
                    .regdt(detail.getAiRegDt())
                    .category(detail.getAcName())
                    .vcount(detail.getAiView())
                    .title(detail.getAiTitle())
                    .nickname(detail.getMiNickname())
                    .miSeq(detail.getAiMiSeq())
                    .aiSeq(detail.getAiSeq())
                    .lcount(detail.getRecommend())
                    .comment(comment)
                    .img(img)
                    .status(true)
                    .message("조회 하였습니다")
                    .code(HttpStatus.OK)
                    .build();
        }
        return response;
    }

    //게시글 수정
    public BasicResponseVO updateArticle(ArticleUpdateVO data, Long miSeq, Long aiSeq) {
        BasicResponseVO response = new BasicResponseVO();
        ArticleInfoEntity article = aiRepo.findByAiSeq(aiSeq);
        MemberInfoEntity member = miRepo.findByMiSeq(miSeq);

        if (member == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 회원이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (article == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 게시글이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (article.getAiMiSeq() == member.getMiSeq()) {
            response = BasicResponseVO.builder()
                    .status(true)
                    .message("수정이 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();

            if (data.getDetail() != "" && data.getTitle() != "") {
                article.setAiTitle(data.getTitle());
                article.setAiDetail(data.getDetail());
                aiRepo.save(article);
            } else if (data.getTitle() == "" && data.getDetail() != "") {
                article.setAiDetail(data.getDetail());
                aiRepo.save(article);
            } else if (data.getDetail() == "" && data.getTitle() != "") {
                article.setAiTitle(data.getTitle());
                aiRepo.save(article);
            }

        } else {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("게시글을 작성한 회원만 수정이 가능합니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        return response;
    }

    //게시글 삭제
    public BasicResponseVO deleteArticle(Long miSeq, Long aiSeq) {
        BasicResponseVO response = new BasicResponseVO();
        ArticleInfoEntity article = aiRepo.findByAiSeq(aiSeq);
        MemberInfoEntity member = miRepo.findByMiSeq(miSeq);
        List<CommentInfoEntity> comment = ciRepo.findAllByCiAiSeq(aiSeq);

        if (member == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 회원이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (article == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 게시글이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (article.getAiMiSeq() == member.getMiSeq()) {
            //이미지 파일 DB와 폴더에서 삭제

            if(comment != null) {
                for(CommentInfoEntity comments : comment)
                ciRepo.delete(comments);
            }

            aiRepo.delete(article);

            response = BasicResponseVO.builder()
                    .status(true)
                    .message("삭제가 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();



            for (ArticlePhotoEntity photo : apRepo.findByApAiSeq(aiSeq)) {

                String oldFileName = photo.getApFileName();
                Path oldfolderLocation = Paths.get(article_image_path);
                Path oldtargetFile = oldfolderLocation.resolve(oldFileName);
                try {
                    Files.delete(oldtargetFile);
                } catch (Exception e) {
                    response = BasicResponseVO.builder()
                            .status(false)
                            .message("기존 파일 삭제에 실패했습니다")
                            .code(HttpStatus.BAD_REQUEST)
                            .build();
                }
                apRepo.delete(photo);
                response = BasicResponseVO.builder()
                        .status(true)
                        .message("삭제가 완료되었습니다")
                        .code(HttpStatus.OK)
                        .build();
            }
        } else {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("게시글을 작성한 회원만 삭제가 가능합니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        return response;
    }

    //댓글작성
    public BasicResponseVO writeComment(CommentWriteVO data, Long miSeq, Long aiSeq) {
        BasicResponseVO response = new BasicResponseVO();
        MemberInfoEntity member = miRepo.findByMiSeq(miSeq);
        ArticleInfoEntity article = aiRepo.findByAiSeq(aiSeq);
        if (member == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 회원이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (article == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 게시글이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (data.getDetail() == null || data.getDetail() == "") {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("내용을 입력해주세요")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else {
            CommentInfoEntity comment = CommentInfoEntity.builder()
                    .ciDetail(data.getDetail())
                    .ciMiSeq(member.getMiSeq())
                    .ciAiSeq(article.getAiSeq())
                    .build();
            ciRepo.save(comment);

            if(data.getImg()!=null) {
                String originalFileName = data.getImg().getOriginalFilename();
                String[] split = originalFileName.split("\\.");
                String ext = split[split.length - 1];

                String newUrl = "comment_" + member.getMiSeq() + LocalDateTime.now().getNano();
                String saveFileName = "comment_" + member.getMiSeq() + LocalDateTime.now().getNano() + "." + ext;

                Path folderLocation = Paths.get(comment_image_path);
                Path targetFile = folderLocation.resolve(saveFileName);

                try {
                    Files.copy(data.getImg().getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    response = BasicResponseVO.builder()
                            .status(false)
                            .message("파일전송에 실패했습니다")
                            .code(HttpStatus.BAD_REQUEST)
                            .build();
                }

                CommentPhotoEntity photoEntity = CommentPhotoEntity.builder()
                        .cpCiSeq(comment.getCiSeq())
                        .cpFileName(saveFileName)
                        .cpFileUrl(newUrl)
                        .build();

                cpRepo.save(photoEntity);
            }
            response = BasicResponseVO.builder()
                    .status(true)
                    .message("댓글등록이 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();
        }
        return response;
    }

    //댓글수정
    public BasicResponseVO updateComment(CommentUpdateVO data, Long miSeq, Long ciSeq) {
        BasicResponseVO response = new BasicResponseVO();
        CommentInfoEntity comment = ciRepo.findByCiSeq(ciSeq);
        MemberInfoEntity member = miRepo.findByMiSeq(miSeq);

        if (member == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 회원이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (comment == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 댓글이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (comment.getCiMiSeq() == member.getMiSeq()) {
            response = BasicResponseVO.builder()
                    .status(true)
                    .message("수정이 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();

         if (data.getDetail() != "") {
                comment.setCiDetail(data.getDetail());
                ciRepo.save(comment);
            } 
         else if (data.getDetail() == "") {
             ciRepo.save(comment);
         }

        } else {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("댓글을 작성한 회원만 수정이 가능합니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        return response;
    }

    //댓글삭제
    public BasicResponseVO deleteComment(Long miSeq, Long ciSeq) {
        BasicResponseVO response = new BasicResponseVO();
        CommentInfoEntity comment = ciRepo.findByCiSeq(ciSeq);
        MemberInfoEntity member = miRepo.findByMiSeq(miSeq);

        if (member == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 회원이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (comment == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 댓글이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (comment.getCiMiSeq() == member.getMiSeq()) {
            //이미지 파일 DB와 폴더에서 삭제
            ciRepo.delete(comment);
            response = BasicResponseVO.builder()
                    .status(true)
                    .message("삭제가 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();

            CommentPhotoEntity photo = cpRepo.findByCpCiSeq(ciSeq);
if(photo != null) {
    String oldFileName = photo.getCpFileName();
    Path oldfolderLocation = Paths.get(comment_image_path);
    Path oldtargetFile = oldfolderLocation.resolve(oldFileName);
    try {
        Files.delete(oldtargetFile);
    } catch (Exception e) {
        response = BasicResponseVO.builder()
                .status(false)
                .message("기존 파일 삭제에 실패했습니다")
                .code(HttpStatus.BAD_REQUEST)
                .build();
    }
    cpRepo.delete(photo);
    response = BasicResponseVO.builder()
            .status(true)
            .message("삭제가 완료되었습니다")
            .code(HttpStatus.OK)
            .build();
}
            }
        else {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("댓글을 작성한 회원만 삭제가 가능합니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        return response;
    }

    //게시글 좋아요
    public BasicResponseVO recommendArticle(Long miSeq, Long aiSeq) {
        BasicResponseVO response = new BasicResponseVO();
        MemberInfoEntity member = miRepo.findByMiSeq(miSeq);
        ArticleInfoEntity article = aiRepo.findByAiSeq(aiSeq);
        ArticleRecoInfoEntity recommend = arRepo.findByArAiSeqAndArMiSeq(aiSeq, miSeq);

        if (member == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 회원이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else if (article == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 게시글이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else if (recommend == null) {
                ArticleRecoInfoEntity reco = ArticleRecoInfoEntity.builder()
                        .arAiSeq(article.getAiSeq())
                        .arMiSeq(member.getMiSeq())
                        .build();
                arRepo.save(reco);
                response = BasicResponseVO.builder()
                        .status(true)
                        .message("좋아요를 눌렀습니다")
                        .code(HttpStatus.OK)
                        .build();

            } else if (recommend != null) {
                arRepo.delete(recommend);
                response = BasicResponseVO.builder()
                        .status(true)
                        .message("좋아요를 취소하였습니다")
                        .code(HttpStatus.OK)
                        .build();
            }
        return response;
    }
}
