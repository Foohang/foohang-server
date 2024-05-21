package com.jjh.foohang.member.model.service;

import com.jjh.foohang.main.fileIO.EFileType;
import com.jjh.foohang.main.service.MainService;
import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberMapper mapper;
    private final MainService mainService;

    private final String defaultProfileImage = "default.png";

    @Override
    public String regist(Member member,  MultipartFile[] file) throws IOException {

        //비번 암호화
        String encodedPassword = mainService.encodeStr(member.getPassword());
        member.setPassword(encodedPassword);

        //회원가입 할 회원에게 주어질 mmeberid
        int registIndex = mapper.getMemberIdMax()+1;
        member.setMemberId(registIndex);

        //프로필 정보 파일로 저장
        List<String> fileName = mainService.saveUplodedFiles(file, "profile_"+registIndex, EFileType.PROFILE_IMAGE);

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

        return mainService.getToken(member);
    }

    @Override
    public String login(Member member) {

        Member m = mapper.login(member);
        System.out.println("서비스 멤버 정보 :"+m);
        if(m == null || !mainService.passwordCompare(member.getPassword(),m.getPassword()))
            return null;

        return mainService.getToken(m);
    }

    @Override
    public Member findMemberById(int id) {

        return mapper.findMemberById(id);
    }

    @Override
    public String updateMember(Member member, MultipartFile[] file, boolean isPasswordChanged) {

        Member updateUserInfo = mapper.findMemberById(member.getMemberId());

        //일반 정보
        updateUserInfo.setNickName(member.getNickName());
        updateUserInfo.setRegion(member.getRegion());
        updateUserInfo.setFood(member.getFood());
        updateUserInfo.setBirth(member.getBirth());
        updateUserInfo.setGender(member.getGender());
        updateUserInfo.setStatusMessage(member.getStatusMessage());

        //프로필 사진
        List<String> fileName = null;

        //프로필 정보 파일로 저장
        try {
            fileName = mainService.saveUplodedFiles(file, "profile_"+member.getMemberId(), EFileType.PROFILE_IMAGE);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("e = " + e);
        }

        if(fileName != null && fileName.size() == 1)
        {
            System.out.println("파일 저장 성공");
            member.setProfileName(fileName.get(0));
        }
        else
        {
            System.out.println("파일 저장 실패");

        }
        System.out.println(fileName);

        String profileName = updateUserInfo.getProfileName();

        if(fileName == null || fileName.size() == 0)
        {
            if(profileName.equals(null) || profileName.equals(""))
                profileName = defaultProfileImage;
        }else
            profileName = fileName.get(0);

        updateUserInfo.setProfileName(profileName);

        //비밀번호
        if(isPasswordChanged)
        {
            String encodedPassword = mainService.encodeStr(member.getPassword());

            System.out.println("인코딩된 비번 비교 = " + encodedPassword);
            mainService.passwordCompare(member.getPassword(),encodedPassword);

            updateUserInfo.setPassword(encodedPassword);
        }

        mapper.updateMember(updateUserInfo);

        return mainService.getToken(updateUserInfo);
    }
}
