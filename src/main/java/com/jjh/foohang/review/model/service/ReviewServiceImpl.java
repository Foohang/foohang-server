package com.jjh.foohang.review.model.service;

import com.jjh.foohang.main.fileIO.EFileType;
import com.jjh.foohang.main.fileIO.FileIO;
import com.jjh.foohang.review.dto.Review;
import com.jjh.foohang.review.model.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewMapper reviewMapper;
    private final FileIO fileIO;

    @Override
    public int addReview(Review review, MultipartFile[] files) {

        List<String> fileList = null;
        //파일 등록

        int reviewIdx = reviewMapper.getReviewIdMax();

        try {
            fileList = fileIO.saveUplodedFiles(files, "file_"+reviewIdx, EFileType.REVIEW_IMAGES);
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        System.out.println(fileList);

        String fileFullName = null;
        if(fileList != null)
        {
            if(fileList.size() > 0)
            {
                fileFullName = "";

                for (String fileName : fileList)
                    fileFullName += "#"+fileName;
            }
        }
        review.setFiles(fileFullName);

        return reviewMapper.insertReview(review);
    }
}
