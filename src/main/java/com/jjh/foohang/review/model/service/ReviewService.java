package com.jjh.foohang.review.model.service;

import com.jjh.foohang.review.dto.Review;
import com.jjh.foohang.review.dto.SelectReviewResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

    //리뷰 추가
    int addReview(Review review, MultipartFile[] files);

    //리뷰 조회
    List<SelectReviewResponse> selectReviewByMemberId(int memberId);
}
