package com.jjh.foohang.member.controller;

import com.jjh.foohang.main.jwtUtil.JWTUtil;
import com.jjh.foohang.main.service.MainService;
import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;
    private final MainService mainService;

    //REST API에서 받는 인자는 @RequestBody랑 @PathVariable만 받아도 무방.

    //회원가입
    //@PostMapping("/regist")
    /*public ResponseEntity<?> regist(@RequestBody Member memberInfo,
                                    @RequestParam("file") MultipartFile file)*/

    @PostMapping("/regist")
    public ResponseEntity<?> regist(
            @RequestPart(value = "file", required = false) MultipartFile[] file,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("nickName") String nickName,
            @RequestPart("region") String region,
            @RequestPart("food") String food,
            @RequestPart("birth") String birth,
            @RequestPart("gender") String gender,
            @RequestPart("statusMessage") String statusMessage
            )
    {
        Member memberInfo = new Member();
        memberInfo.setEmail(email);
        memberInfo.setPassword(password);
        memberInfo.setNickName(nickName);
        memberInfo.setRegion(region);
        memberInfo.setFood(food);
        memberInfo.setBirth(birth);
        memberInfo.setGender(Integer.parseInt(gender));
        memberInfo.setStatusMessage(statusMessage);

        String token = null;
        try {
            token = service.regist(memberInfo, file);
        }catch(IOException e) {
            e.printStackTrace();
        }


        if(token == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가입 실패");

        return ResponseEntity.ok(token);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member loginInfo)
    {
        System.out.println("로그인 정보: "+loginInfo);

        String token = service.login(loginInfo);

        if(token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 잘못되었습니다.");

        return ResponseEntity.ok(token);
    }
    @GetMapping("/oauth")
    @ResponseBody
    public String kakaoOauth(@RequestParam("code") String code) {

        return "okay";
    }

    //회원 정보 수정



    @PutMapping("/")
    public ResponseEntity<?> update(
            @RequestParam(value = "profile", required = false) MultipartFile[] file,
                                    @RequestParam("password") String password,
                                    @RequestParam("nickName") String nickName,
                                    @RequestParam("region") String region,
                                    @RequestParam("food") String food,
                                    @RequestParam("birth") String birth,
                                    @RequestParam("gender") int gender,
                                    @RequestParam("statusMessage") String statusMessage,
                                    @RequestHeader("Authorization") String tokenHeader)
    {
        //id값으로 회원 정보 가져오기
        Member member =mainService.checkUser(tokenHeader);

        //회원정보가 존재하는지 체크
        if(member == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("토큰 안맞음");

        member.setNickName(nickName);
        member.setRegion(region);
        member.setFood(food);
        member.setBirth(birth);
        member.setGender(gender);
        member.setStatusMessage(statusMessage);

        String updateToken = "";

        if(!password.equals(""))
        {
            member.setPassword(password);
            updateToken = service.updateMember(member, file, true);
        }
        else
            updateToken = service.updateMember(member, file, false);

        System.out.println(updateToken);

        return ResponseEntity.ok(updateToken);
    }

    //kakaoLogin
    @GetMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(){
        return ResponseEntity.ok().build();
    }

}
