package com.jjh.foohang.member.controller;

import com.jjh.foohang.main.jwtUtil.JWTUtil;
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
    private final JWTUtil jwtUtil;

    //REST API에서 받는 인자는 @RequestBody랑 @PathVariable만 받아도 무방.

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

    //회원 정보 수정
/*    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Member updateInfo,
                                    @RequestHeader("Authorization") String tokenHeader) {}*/

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody Member updateInfo)
    {
        //id값으로 회원 정보 가져오기
        Member member = service.findMemberById(updateInfo.getMemberId());

        //회원정보가 존재하는지 체크
        if(member == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원정보 없음");


        //회원과 토큰정보가 일치하는지 체크

        //String memberToken = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        //if(m.getMemberId().equals(memberToken));

        //일치하면 수정

        //비밀번호 입력되지않을때

        member.setPassword(updateInfo.getPassword());
        member.setNickName(updateInfo.getNickName());
        member.setRegion(updateInfo.getRegion());
        member.setFood(updateInfo.getFood());
        member.setBirth(updateInfo.getBirth());
        member.setGender(updateInfo.getGender());

        if(updateInfo.getPassword() == null)
            service.updateNotIncludePassword(member);
        else
            service.updateIncludePassword(member);

        return ResponseEntity.ok().build();
    }
}
