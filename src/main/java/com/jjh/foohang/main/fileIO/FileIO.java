package com.jjh.foohang.main.fileIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileIO {

    @Value("${file.upload.directory}")
    private String LOCAL_PATH;

    private String getAdditionalPath(EFileType fileType)
    {
        switch(fileType)
        {
            case PROFILE_IMAGE:
                return "profile/";
            case REVIEW_IMAGES:
                return "review/";
        }

        return "";
    }

    //성공 시 저장한 파일 명 반환
    public List<String> saveUplodedFiles(MultipartFile[] files, int memberId, EFileType fileType) throws IOException
    {
        String fileName = null;
        List<String> fileNameList = new ArrayList<>();
        if(files != null)
        {
            for(MultipartFile file : files)
            {
                if(file.isEmpty())
                {
                    System.out.println("파일이 존재하지 않습니다.");
                    return null;
                }
                try {
                    byte[] bytes = file.getBytes();

                    //확장자 추출
                    fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    fileName = memberId+fileName;
                    Path path = Paths.get(LOCAL_PATH+getAdditionalPath(fileType), fileName);
                    Files.write(path, bytes);

                    fileNameList.add(fileName);

                }catch (IOException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return fileNameList;
    }
    public Resource downlodedFile(String fileName, EFileType fileType) throws IOException
    {
        try
        {
            Path filePath = Paths.get(LOCAL_PATH+getAdditionalPath(fileType)).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(!resource.exists())
                return null;

            return resource;

        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
