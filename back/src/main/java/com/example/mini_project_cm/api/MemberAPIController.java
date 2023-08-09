package com.example.mini_project_cm.api;


import com.example.mini_project_cm.service.MemberService;
import com.example.mini_project_cm.vo.BasicResponseVO;
import com.example.mini_project_cm.vo.member.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원관리", description = "회원관리 CRUD")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberAPIController {
    private final MemberService mService;

    @Operation(summary = "회원가입", description = "사용자로부터 데이터를 받아 회원가입을 진행합니다")
    @PutMapping("/join")
    public ResponseEntity<BasicResponseVO> memberJoin(@RequestBody MemberJoinVO data) {
        return new ResponseEntity<>(mService.joinMember(data),HttpStatus.OK);
    }
    @Operation(summary = "회원 일반 로그인", description = "회원에게 값을 받아 로그인을 합니다")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseVO> memberLogin(@RequestBody LoginVO data) {
        return new ResponseEntity<>(mService.Login(data), HttpStatus.OK);
    }
    @Operation(summary = "회원 이메일 중복체크", description = "회원이 입력한 이메일의 중복체크를 합니다")
    @PostMapping("/dupchkEmail")
    public ResponseEntity<DupchkVO> dupChkEmail(@RequestBody EmailVO data) {
        return new ResponseEntity<>(mService.dupcheckMemberEmail(data), HttpStatus.OK);
    }
    @Operation(summary = "회원 닉네임 중복체크", description = "회원이 입력한 닉네임의 중복체크를 합니다")
    @PostMapping("/dupchkNickName")
    public ResponseEntity<DupchkVO> dupChkEmail(@RequestBody NickNameVO data) {
        return new ResponseEntity<>(mService.dupcheckNickName(data), HttpStatus.OK);
    }
    @Operation(summary = "회원 탈퇴", description = "회원번호를 받아 해당하는 회원을 탈퇴처리 합니다")
    @PostMapping("/leave/{seq}")
    public ResponseEntity<BasicResponseVO> memberLeave(@PathVariable Long seq) {
        return new ResponseEntity<>(mService.leaveMember(seq), HttpStatus.OK);
    }
    @Operation(summary = "회원정보 변경", description = "사용자로부터 데이터를 받아 회원정보변경을 진행합니다")
    @PatchMapping("/change/memberInfo/{seq}")
    public ResponseEntity<BasicResponseVO> changePwd(@PathVariable Long seq, @RequestBody MemberInfoVO data) throws Exception{
        return new ResponseEntity<>(mService.changeMemberInfo(seq, data),HttpStatus.OK);
    }
    @Operation(summary = "회원 사진 등록", description = "회원번호를 통해 회원의 사진을 등록합니다")
    @PutMapping(value = "/insert/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponseVO> insertMemberPhoto(
            @Parameter(description = "formdata로 이미지를 입력합니다")
            @ModelAttribute MemberImageVO data
    ) {
        return new ResponseEntity<>(mService.insertMemberPhoto(data), HttpStatus.OK);
    }
    @Operation(summary = "비밀번호 찾기", description = "이메일 주소를 통해 임시 비밀번호를 이메일로 발급받습니다")
    @PostMapping("/search/pwd")
    public ResponseEntity<BasicResponseVO> memberSearchPwd(@RequestBody EmailVO data) throws Exception{
        return new ResponseEntity<>(mService.searchMemberPwd(data), HttpStatus.OK);
    }
    @Operation(summary = "마이페이지 정보출력", description = "회원번호를 통해 회원의 정보를 출력합니다")
    @GetMapping("/get/memberInfo/{seq}")
    public ResponseEntity<MyPageVO> memberGetInfo(@PathVariable Long seq) {
        return new ResponseEntity<>(mService.getMemberInfo(seq), HttpStatus.OK);
    }
    @Operation(summary = "마이페이지 게시글출력", description = "회원번호를 통해 회원이 쓴 게시글을 출력합니다")
    @GetMapping("/get/memberInfo/article/{seq}")
    public ResponseEntity<MemberArticleVO> memberGetArticle(@PathVariable Long seq) {
        return new ResponseEntity<>(mService.getMemberArticle(seq), HttpStatus.OK);
    }
    @Operation(summary = "마이페이지 댓글출력", description = "회원번호를 통해 회원이 쓴 댓글을 출력합니다")
    @GetMapping("/get/memberInfo/comment/{seq}")
    public ResponseEntity<MemberCommentVO> memberGetComment(@PathVariable Long seq) {
        return new ResponseEntity<>(mService.getMemberComment(seq), HttpStatus.OK);
    }
    @Operation(summary = "마이페이지 스크랩 게시물 출력", description = "회원번호를 통해 회원이 스크랩한 게시물 출력합니다")
    @GetMapping("/get/memberInfo/scrap/{seq}")
    public ResponseEntity<MemberArticleVO> memberGetScrap(@PathVariable Long seq) {
        return new ResponseEntity<>(mService.getMemberScrap(seq), HttpStatus.OK);
    }
}
