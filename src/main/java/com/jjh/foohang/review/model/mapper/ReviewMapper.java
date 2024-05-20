package com.jjh.foohang.review.model.mapper;

import com.jjh.foohang.review.dto.Review;
import com.jjh.foohang.review.dto.SelectReviewResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    //리뷰 등록
    int insertReview(Review review);

    //리뷰 번호 조회
    int getReviewIdMax();

    //리뷰 조회
    List<Review> selectReviewByMemberId(int memberId);

    //리뷰 삭제
    int deleteReview(int reviewId);
}
