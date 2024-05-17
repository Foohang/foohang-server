package com.jjh.foohang.member.model.service;

import com.jjh.foohang.member.dto.Member;

public interface MemberService {

    //회원 가입
    String regist(Member member);

    //내 회원 정보 조회 (로그인)
    String login(Member member);

    //회원 정보 id값으로 찾기
    Member findMemberById(int id);

    //회원 정보 수정하기
    //회원 정보 수정하기
    String updateIncludePassword(Member member);

    String updateNotIncludePassword(Member member);
}
