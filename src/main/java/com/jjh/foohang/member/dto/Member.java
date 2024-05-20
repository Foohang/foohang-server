package com.jjh.foohang.member.dto;

import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Data
public class Member {
    private int     memberId; //primary
    private String  email;
    private String  password;
    private String  nickName;
    private String  region;
    private String  food;
    private String  birth;
    private int     gender;
    private String  token;
    private String  statusMessage;
    private String  profileName;
    //private Resource profile;
}
