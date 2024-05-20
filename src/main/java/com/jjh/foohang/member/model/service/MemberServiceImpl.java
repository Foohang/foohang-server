package com.jjh.foohang.member.model.service;

import com.jjh.foohang.main.fileIO.EFileType;
import com.jjh.foohang.main.fileIO.FileIO;
import com.jjh.foohang.main.jwtUtil.JWTUtil;
import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberMapper mapper;

    //인증 토큰 관련
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final FileIO fileIo;

    private final String defaultProfileImage = "default.png";

    @Override
    public String regist(Member member,  MultipartFile[] file) throws IOException {

        //비번 암호화
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        //회원가입 할 회원에게 주어질 mmeberid
        int registIndex = mapper.getMemberIdMax()+1;
        member.setMemberId(registIndex);

        //프로필 정보 파일로 저장
        List<String> fileName = fileIo.saveUplodedFiles(file, "profile_"+registIndex, EFileType.PROFILE_IMAGE);

        if(fileName != null && fileName.size() == 1)
        {
            System.out.println("파일 저장 성공");
            member.setProfileName(fileName.get(0));
        }
        else
            System.out.println("파일 저장 실패");

        System.out.println(fileName);

        String profileName = (fileName == null || fileName.size() == 0) ? defaultProfileImage :  fileName.get(0);
        member.setProfileName(profileName);
        //저장한 파일로 프로필 파일 이름 등록
        /*Resource res = fileIo.downlodedFile(profileName, EFileType.PROFILE_IMAGE);
        if(res == null)
            System.out.println("파일 불러오기 싪패");
        else
            System.out.println("파일 불러오기 성궁"+res);

        member.setProfile(res);*/

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

    @Override
    public Member findMemberById(int id) {

        return mapper.findMemberById(id);
    }

    @Override
    public String updateIncludePassword(Member member) {

        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        mapper.updateIncludePassword(member);
        Member updateUserInfo = mapper.findMemberById(member.getMemberId());
        return jwtUtil.generateToken(updateUserInfo);
    }

    @Override
    public String updateNotIncludePassword(Member member) {
        mapper.updateNotIncludePassword(member);
        Member updateUserInfo = mapper.findMemberById(member.getMemberId());
        return jwtUtil.generateToken(updateUserInfo);
    }

}
