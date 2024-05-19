package com.jjh.foohang.review.model.service;

import com.jjh.foohang.main.fileIO.EFileType;
import com.jjh.foohang.main.fileIO.FileIO;
import com.jjh.foohang.review.dto.Review;
import com.jjh.foohang.review.dto.SelectReviewResponse;
import com.jjh.foohang.review.model.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewMapper reviewMapper;
    private final FileIO fileIO;

    private List<String> hashTagParse(String str, boolean isHashTag)
    {
        // 문자열을 '#' 기준으로 분할 (빈 문자열 제거)
        String[] parts = str.split("#");

        // 빈 문자열 제거를 위해 필터링
        List<String> fileList = Arrays.stream(parts)
                .filter(s -> !s.isEmpty())
                .toList();

        if(isHashTag)
            for(int i = 0; i<fileList.size(); i++)
                fileList.set(i, '#'+fileList.get(i));

        // 결과 출력
        System.out.println(fileList);

        return fileList;
    }

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

    @Override
    public List<SelectReviewResponse> selectReviewByMemberId(int memberId) {

        List<SelectReviewResponse> reviewResponseList = new ArrayList<>();
        List<Review> reviewList = reviewMapper.selectReviewByMemberId(memberId);

        for(Review review : reviewList)
        {
            SelectReviewResponse srr = new SelectReviewResponse();

            srr.setTitle(review.getReviewTitle());
            srr.setPostDate(review.getUploadedDate());
            srr.setTravelDate(review.getSelectedDate());
            srr.setContent(review.getReviewText());
            srr.setSelectedEmotion(review.getSelectedEmotion());

            List<String> hashtags = hashTagParse(review.getHashtags(), true);
            List<String> files = hashTagParse(review.getFiles(), false);

            //파일
            String url = "http://localhost/files/";

            for(int i = 0; i<files.size(); i++)
                files.set(i, url+files.get(i));

            srr.setHashtags(hashtags);
            srr.setImages(files);

            reviewResponseList.add(srr);
        }

        return reviewResponseList;
    }
}
