package com.jjh.foohang.main.service;

import com.jjh.foohang.main.fileIO.EFileType;
import com.jjh.foohang.main.fileIO.FileIO;
import com.jjh.foohang.main.jwtUtil.JWTUtil;
import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService
{
    private final MemberMapper memberMapper;
    private final JWTUtil jwtUtil;
    //인증 토큰 관련
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final FileIO fileIo;

    @Override
    public Member checkUser(String token) {
        //"Baerer "를 제외한 토큰
        int memberId = jwtUtil.getMemberIdFromToken(token.substring(7));

        Member authMember = memberMapper.findMemberById(memberId);

        if(authMember == null)
            return null;

        return authMember;
    }

    @Override
    public String encodeStr(String str) {
        return passwordEncoder.encode(str);
    }

    @Override
    public boolean passwordCompare(String p1, String p2) {
        return passwordEncoder.matches(p1, p2);
    }

    @Override
    public List<String> saveUplodedFiles(MultipartFile[] files, String prefix, EFileType fileType) throws IOException {
        return fileIo.saveUplodedFiles(files, prefix, fileType);
    }

    @Override
    public Resource downlodedFile(String fileName, EFileType fileType) throws IOException {
        return fileIo.downlodedFile(fileName, fileType);
    }

    @Override
    public String getToken(Member member) {
        return jwtUtil.generateToken(member);
    }


}
