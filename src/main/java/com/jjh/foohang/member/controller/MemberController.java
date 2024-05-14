package com.jjh.foohang.member.controller;

import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    //회원가입
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody Member memberInfo)
    {
        String token = service.regist(memberInfo);

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
}
