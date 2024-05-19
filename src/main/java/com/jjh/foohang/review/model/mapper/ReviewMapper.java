package com.jjh.foohang.review.model.mapper;

import com.jjh.foohang.review.dto.Review;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {

    //리뷰 등록
    int insertReview(Review review);
    //리뷰 조회
}
