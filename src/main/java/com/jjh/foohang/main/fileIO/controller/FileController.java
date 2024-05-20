package com.jjh.foohang.main.fileIO.controller;

import com.jjh.foohang.main.fileIO.EFileType;
import com.jjh.foohang.main.fileIO.FileIO;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.IIOException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileIO fileIo;
    @GetMapping("/{fileType}/{fileName}")
    public ResponseEntity<?>downloadFile(@PathVariable String fileName, @PathVariable String fileType)
    {
        EFileType eFileType;

        if(fileType == "profile")
            eFileType = EFileType.PROFILE_IMAGE;
        else if(fileType == "review")
            eFileType = EFileType.REVIEW_IMAGES;
        else
            eFileType =EFileType.UNDEFINED;

        Resource res = null;
        try{
            res = fileIo.downlodedFile(fileName, eFileType);
        }catch(IOException e){
            e.printStackTrace();
        }

        if(res == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일을 찾을 수 없음");

        return ResponseEntity.ok(res);
    }
}
