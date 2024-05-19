package com.jjh.foohang.review.model.service;

import com.jjh.foohang.review.dto.Review;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {

    //리뷰 추가
    int addReview(Review review, MultipartFile[] files);
}
