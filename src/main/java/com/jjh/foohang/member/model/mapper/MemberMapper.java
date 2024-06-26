package com.jjh.foohang.member.model.mapper;

import com.jjh.foohang.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    //회원 가입
    int regist(Member member);

    //내 회원 정보 조회 (로그인)
    Member login(Member member);

    //회원 정보 id값으로 찾기
    Member findMemberById(int id);

    //회원 정보 수정하기
    int updateIncludePassword(Member member);

    int updateNotIncludePassword(Member member);
}
