package com.jjh.foohang.member.model.service;

import com.jjh.foohang.member.dto.Member;

public interface MemberService {

    //회원 가입
    String regist(Member member);

    //내 회원 정보 조회 (로그인)
    String login(Member member);


}
