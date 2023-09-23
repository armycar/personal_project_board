package com.example.mini_project_cm.service;

import com.example.mini_project_cm.entity.*;
import com.example.mini_project_cm.repository.*;
import com.example.mini_project_cm.utils.AESAlgorithm;
import com.example.mini_project_cm.vo.BasicResponseVO;
import com.example.mini_project_cm.vo.article.ArticleGetVO;
import com.example.mini_project_cm.vo.comment.CommentGetVO;
import com.example.mini_project_cm.vo.member.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberInfoRepository miRepo;
    private final MemberPhotoRepository mpRepo;
    private final JavaMailSender javaMailSender;
    private final MyPageViewRepository mvRepo;
    private final ArticleInfoRepository aiRepo;
    private final CommentInfoRepository ciRepo;
    private final CommentViewRepository cvRepo;
    private final ArticleScrapRepository asRepo;

    @Value("${file.image.member}") String member_image_path;

    //회원가입
    public BasicResponseVO joinMember(MemberJoinVO data) {
        BasicResponseVO response = new BasicResponseVO();
        String pattern = "^[a-zA-Z0-9!@#$%^&*()-_=+]*$";

        if (data.getPwd().length() < 8) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("비밀번호는 8자리 이상입니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (!data.getPwd().equals(data.getPwdconfirm())) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("비밀번호를 다시 확인해주세요")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (!Pattern.matches(pattern, data.getPwd())) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("비밀번호에 포함할 수 없는 문자나 공백이 있습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (!Pattern.matches(pattern, data.getNickname())) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("닉네임에 포함할 수 없는 문자나 공백이 있습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else {
            try {
                String encPwd = AESAlgorithm.Encrypt(data.getPwd());
                data.setPwd(encPwd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            MemberInfoEntity member = MemberInfoEntity.builder()
                    .miEmail(data.getEmail())
                    .miNickName(data.getNickname())
                    .miPwd(data.getPwd())
                    .miRole("USER")
                    .build();

            miRepo.save(member);

            response = BasicResponseVO.builder()
                    .status(true)
                    .message("회원가입이 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();
        }
        return response;
    }

    //로그인
    public LoginResponseVO Login(LoginVO data) {
        LoginResponseVO response = new LoginResponseVO();
        MemberInfoEntity loginUser = null;
        try {
            loginUser = miRepo.findTop1ByMiEmailAndMiPwd(
                    data.getEmail(), AESAlgorithm.Encrypt(data.getPwd())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (loginUser == null) {
            response = LoginResponseVO.builder()
                    .status(false)
                    .message("이메일 또는 비밀번호 오류입니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (loginUser.getMiStatus() == 2) {
            response = LoginResponseVO.builder()
                    .status(false)
                    .message("활동이 정지된 계정입니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (loginUser.getMiStatus() == 3) {
            response = LoginResponseVO.builder()
                    .status(false)
                    .message("탈퇴 진행중인 계정입니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else {
            response = LoginResponseVO.builder()
                    .miSeq(loginUser.getMiSeq())
                    .nickname(loginUser.getMiNickName())
                    .status(true)
                    .message("로그인 되었습니다")
                    .code(HttpStatus.OK)
                    .build();
        }
        return response;
    }

    //이메일(아이디) 중복체크
    public DupchkVO dupcheckMemberEmail(EmailVO data) {
        DupchkVO response = new DupchkVO();

        if (miRepo.countByMiEmail(data.getEmail()) != 0) {
            response = new DupchkVO().builder()
                    .count(1)
                    .status(false)
                    .message("이미 존재하는 이메일입니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else {
            response = new DupchkVO().builder()
                    .count(0)
                    .status(true)
                    .message("사용 가능한 이메일입니다")
                    .code(HttpStatus.OK)
                    .build();
        }
        return response;
    }

    // 닉네임 중복체크
    public DupchkVO dupcheckNickName(NickNameVO data) {
        DupchkVO response = new DupchkVO();

        if (miRepo.countByMiNickName(data.getNickname()) != 0) {
            response = new DupchkVO().builder()
                    .count(1)
                    .status(false)
                    .message("이미 존재하는 닉네임입니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else {
            response = new DupchkVO().builder()
                    .count(0)
                    .status(true)
                    .message("사용 가능한 닉네임입니다")
                    .code(HttpStatus.OK)
                    .build();
        }
        return response;
    }

    // 회원 탈퇴
    public BasicResponseVO leaveMember(Long seq) {
        BasicResponseVO response = new BasicResponseVO();
        MemberInfoEntity loginUser = miRepo.findByMiSeq(seq);
        if (loginUser == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 회원이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else if (loginUser.getMiStatus() != 1) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("정지된 계정이거나 이미 탈퇴처리된 회원입니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else {
            Date now = new Date();
            loginUser.setMiStatus(3);
            loginUser.setMiLeaveDt(now);
            miRepo.save(loginUser);
            response = BasicResponseVO.builder()
                    .status(true)
                    .message("탈퇴처리가 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();
        }
        return response;
    }

    //회원정보변경
    public BasicResponseVO changeMemberInfo(Long seq, MemberInfoVO data) throws Exception {
        BasicResponseVO response = new BasicResponseVO();
        String pattern = "^[a-zA-Z0-9!@#$%^&*()-_=+]*$";

        MemberInfoEntity loginUser = miRepo.findByMiSeq(seq);
        if (loginUser == null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 회원이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        } else {
            if (data.getNickname() != "") {
                if (!Pattern.matches(pattern, data.getNickname())) {
                    response = BasicResponseVO.builder()
                            .status(false)
                            .message("닉네임에 포함할 수 없는 문자나 공백이 있습니다")
                            .code(HttpStatus.BAD_REQUEST)
                            .build();
                    return response;
                }
                if (data.getNickname().isEmpty()) {
                    data.setNickname(loginUser.getMiNickName());
                }
                loginUser.setMiNickName(data.getNickname());
            }

            if (data.getOldpwd() != "" && data.getPwd() != "" && data.getPwdconfirm() != "") {
                if (!loginUser.getMiPwd().equals(AESAlgorithm.Encrypt(data.getOldpwd()))) {
                    response = BasicResponseVO.builder()
                            .status(false)
                            .message("기존 비밀번호가 틀립니다")
                            .code(HttpStatus.BAD_REQUEST)
                            .build();
                    return response;
                }
                if (!Pattern.matches(pattern, data.getPwd())) {
                    response = BasicResponseVO.builder()
                            .status(false)
                            .message("비밀번호에 포함할 수 없는 문자나 공백이 있습니다")
                            .code(HttpStatus.BAD_REQUEST)
                            .build();
                    return response;
                }
                if (data.getPwd().length() < 8) {
                    response = BasicResponseVO.builder()
                            .status(false)
                            .message("비밀번호는 8자리 이상입니다")
                            .code(HttpStatus.BAD_REQUEST)
                            .build();
                    return response;
                }
                if (!data.getPwd().equals(data.getPwdconfirm())) {
                    response = BasicResponseVO.builder()
                            .status(false)
                            .message("비밀번호를 다시 확인해주세요")
                            .code(HttpStatus.BAD_REQUEST)
                            .build();
                    return response;
                }
                try {
                    String encPwd = AESAlgorithm.Encrypt(data.getPwd());
                    data.setPwd(encPwd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loginUser.setMiPwd(data.getPwd());
            }

            miRepo.save(loginUser);

            response = BasicResponseVO.builder()
                    .status(true)
                    .message("회원정보 변경이 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();
        }

        return response;
    }

    //회원 이미지 추가
    public BasicResponseVO insertMemberPhoto(MemberImageVO data) {
        BasicResponseVO response = new BasicResponseVO();
        MemberInfoEntity member = miRepo.findByMiSeq(data.getSeq());
        MemberPhotoEntity photo = mpRepo.findByMpMiSeq(data.getSeq());

        if(member==null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당 회원은 존재하지 않습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else if(photo == null) {
            String originalFileName = data.getImg().getOriginalFilename();
            String[] split = originalFileName.split("\\.");
            String ext = split[split.length -1];

            String newUrl = "member_"+member.getMiSeq()+ LocalDateTime.now().getNano();
            String saveFileName = "member_"+member.getMiSeq()+LocalDateTime.now().getNano()+"."+ext;

            Path folderLocation = Paths.get(member_image_path);
            Path targetFile = folderLocation.resolve(saveFileName);

            try {
                Files.copy(data.getImg().getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (Exception e) {
                response = BasicResponseVO.builder()
                        .status(false)
                        .message("파일전송에 실패했습니다")
                        .code(HttpStatus.BAD_REQUEST)
                        .build();
            }

            MemberPhotoEntity photoEntity = MemberPhotoEntity.builder()
                    .mpMiSeq(member.getMiSeq())
                    .mpFileName(saveFileName)
                    .mpFileUrl(newUrl)
                    .build();

            mpRepo.save(photoEntity);
            response = BasicResponseVO.builder()
                    .status(true)
                    .message("사진 등록이 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();
        }
        else {
            String oldFileName = photo.getMpFileName();
            Path oldfolderLocation = Paths.get(member_image_path);
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
            mpRepo.delete(photo);

            String originalFileName = data.getImg().getOriginalFilename();
            String[] split = originalFileName.split("\\.");
            String ext = split[split.length -1];

            String newUrl = "member_"+member.getMiSeq()+ LocalDateTime.now().getNano();
            String saveFileName = "member_"+member.getMiSeq()+LocalDateTime.now().getNano()+"."+ext;

            Path folderLocation = Paths.get(member_image_path);
            Path targetFile = folderLocation.resolve(saveFileName);

            try {
                Files.copy(data.getImg().getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (Exception e) {
                response = BasicResponseVO.builder()
                        .status(false)
                        .message("파일전송에 실패했습니다")
                        .code(HttpStatus.BAD_REQUEST)
                        .build();
            }

            MemberPhotoEntity photoEntity = MemberPhotoEntity.builder()
                    .mpMiSeq(member.getMiSeq())
                    .mpFileName(saveFileName)
                    .mpFileUrl(newUrl)
                    .build();

            mpRepo.save(photoEntity);
            response = BasicResponseVO.builder()
                    .status(true)
                    .message("사진 등록이 완료되었습니다")
                    .code(HttpStatus.OK)
                    .build();
        }
        return response;
    }

    //이메일로 비밀번호 찾기
    public BasicResponseVO searchMemberPwd(EmailVO data) throws Exception{
        BasicResponseVO response = new BasicResponseVO();

        MemberInfoEntity member = miRepo.findByMiEmail(data.getEmail());

        if(member==null) {
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("해당하는 이메일이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else {
            String tempPassword = String.valueOf(LocalDateTime.now().getNano())+member.getMiSeq();

            try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(data.getEmail());
            helper.setSubject("임시 비밀번호 안내");
            helper.setText("<p>안녕하세요. 회원님의 임시 비밀번호는 " + tempPassword + "입니다.</p>", true);

            javaMailSender.send(message);

                    String encPwd = AESAlgorithm.Encrypt(tempPassword);
                    member.setMiPwd(encPwd);
                    miRepo.save(member);

            response = BasicResponseVO.builder()
                    .status(true)
                    .message("입력하신 이메일 주소로 임시 비밀번호를 발송했습니다.")
                    .code(HttpStatus.OK)
                    .build();

        } catch (MessagingException e) {
            e.printStackTrace();
            // 이메일 발송 실패 시 처리
            response = BasicResponseVO.builder()
                    .status(false)
                    .message("임시 비밀번호 발송에 실패했습니다.")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
        }

    return response;
    }
    //회원정보 조회(마이페이지)
    public MyPageVO getMemberInfo(Long seq) {
        MyPageVO response = new MyPageVO();

        MyPageViewEntity member = mvRepo.findByMiSeq(seq);
        if(member == null) {
            response = MyPageVO.builder()
                    .status(false)
                    .message("해당하는 회원이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else{
            response = MyPageVO.builder()
                    .status(true)
                    .message("조회에 성공하였습니다")
                    .code(HttpStatus.OK)
                    .seq(member.getMiSeq())
                    .nickname(member.getMiNickname())
                    .email(member.getMiEmail())
                    .url(member.getMpFileUrl())
                    .build();
        }
        return response;
    }
    // 작성한 게시글 보기
    public MemberArticleVO getMemberArticle(Long seq) {
        MemberArticleVO response = new MemberArticleVO();

        List<ArticleGetVO> article = aiRepo.findByAiMiSeq(seq);
        if(article==null) {
            response = MemberArticleVO.builder()
                    .status(false)
                    .message("작성한 게시글이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else {
            response = MemberArticleVO.builder()
                    .articles(article)
                    .status(true)
                    .message("조회에 성공 하였습니다")
                    .code(HttpStatus.OK)
                    .build();
        }

        return response;
    }

    // 작성한 댓글 보기
    public MemberCommentVO getMemberComment(Long seq) {
        MemberCommentVO response = new MemberCommentVO();

        List<CommentGetVO> comment = cvRepo.findByCiMiSeq(seq);
        if(comment==null) {
            response = MemberCommentVO.builder()
                    .status(false)
                    .message("작성한 댓글이 없습니다")
                    .code(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else {
            response = MemberCommentVO.builder()
                    .comments(comment)
                    .status(true)
                    .message("조회에 성공 하였습니다")
                    .code(HttpStatus.OK)
                    .build();
        }

        return response;
    }

    // 스크랩한 게시글 보기
    public MemberArticleVO getMemberScrap(Long seq) {
        MemberArticleVO response = new MemberArticleVO();

        List<ArticleGetVO> articles = new ArrayList<>();

        List<ArticleScrapEntity> members = asRepo.findByAsMiSeq(seq);
        for (ArticleScrapEntity member : members) {
            if (member != null) {
                List<ArticleGetVO> article = aiRepo.findListByAiSeq(member.getAsAiSeq());
                articles.addAll(article);
            }
        }

        if (!articles.isEmpty()) {
            response = MemberArticleVO.builder()
                    .articles(articles)
                    .status(true)
                    .message("조회에 성공 하였습니다")
                    .code(HttpStatus.OK)
                    .build();
        }
        else {
            response = MemberArticleVO.builder()
                    .status(false)
                    .message("스크랩한 게시물이 없습니다")
                    .code(HttpStatus.OK)
                    .build();
        }

        return response;
    }

    //회원리스트(관리자전용)
    public Page<MemberListVO> readMemberList(String type, String keyword, Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 5;
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("miRegDt").descending());
        Page<MemberListVO> response = null;

        if(type.equals("all")) {
            response = miRepo.findAll(pageRequest);
        } else if (type.equals("nickname")) {
            response = miRepo.searchNickname(keyword, pageRequest);
        }
        return response;
    }

}

