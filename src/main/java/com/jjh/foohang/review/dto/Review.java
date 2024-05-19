package com.jjh.foohang.review.dto;

import lombok.Data;

@Data
public class Review {

    private int     reviewId;
    private int     memberId;
    private String  selectedDate;       // 게시글 작성 날짜
    private String  uploadedDate;        // 업로드 날짜
    private String  hashtags;           // 해시태그, #마다 파싱 필요
    private String  reviewTitle;        // 제목
    private String  reviewText;         // 리뷰
    private String  files;              // n개 이상의 파일명 통짜문자열
    private int  selectedEmotion;       // 0: 설정 없음

}
