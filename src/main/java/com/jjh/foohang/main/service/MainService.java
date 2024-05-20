package com.jjh.foohang.main.service;

import com.jjh.foohang.main.fileIO.EFileType;
import com.jjh.foohang.member.dto.Member;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//공통으로 쓸 기능들 모아놓은 서비스
public interface MainService {

    //인증확인
    Member checkUser(String token);

    String encodeStr(String str);

    boolean passwordCompare(String p1, String p2);

    List<String> saveUplodedFiles(MultipartFile[] files, String prefix, EFileType fileType) throws IOException;

    Resource downlodedFile(String fileName, EFileType fileType) throws IOException;

    String getToken(Member member);
}
