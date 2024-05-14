package com.jjh.foohang.member.model.mapper;

import com.jjh.foohang.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    //회원 가입
    int regist(Member member);

    //내 회원 정보 조회 (로그인)
    Member login(Member member);

}
