package com.jjh.foohang.member.model.service;

import com.jjh.foohang.main.jwtUtil.JWTUtil;
import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberMapper mapper;

    //인증 토큰 관련
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String regist(Member member) {

        //비번 암호화
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        //DB에 저장
        mapper.regist(member);

        return jwtUtil.generateToken(member);
    }

    @Override
    public String login(Member member) {



        Member m = mapper.login(member);
        System.out.println("서비스 멤버 정보 :"+m);
        if(m == null || !passwordEncoder.matches(member.getPassword(),m.getPassword()))
            return null;


        return jwtUtil.generateToken(m);
    }
}
