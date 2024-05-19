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

@Component
public class FileIO {

    @Value("${file.upload.directory}")
    private String LOCAL_PATH;

    //성공 시 저장한 파일 명 반환
    public String saveUplodedFiles(MultipartFile files, int memberId) throws IOException
    {
        String fileName = null;

        //for(MultipartFile file : files)
        //{
            if(files.isEmpty())
            {
                System.out.println("파일이 존재하지 않습니다.");
                return null;
            }
            try {
                byte[] bytes = files.getBytes();

                //확장자 추출
                fileName = files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf("."));
                fileName = memberId+fileName;
                Path path = Paths.get(LOCAL_PATH+"profile/", fileName);
                Files.write(path, bytes);

            }catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
        //}
        return fileName;
    }
    public Resource downlodedFile(String fileName) throws IOException
    {
        try
        {
            Path filePath = Paths.get(LOCAL_PATH+"profile/").resolve(fileName).normalize();
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
