package com.jjh.foohang.review.controller;

import com.jjh.foohang.main.jwtUtil.JWTUtil;
import com.jjh.foohang.main.service.MainService;
import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.service.MemberService;
import com.jjh.foohang.review.dto.Review;
import com.jjh.foohang.review.dto.SelectReviewResponse;
import com.jjh.foohang.review.model.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final MainService mainService;

    //게시글 등록
    @PostMapping("/")
    public ResponseEntity<?> addReview(
            @RequestParam("selectedDate") String selectedDate,
            @RequestParam("uploadedDate") String uploadedDate,
            @RequestParam("hashtags") String hashtags,
            @RequestParam("reviewTitle") String reviewTitle,
            @RequestParam("reviewText") String reviewText,
            @RequestParam("selectedEmotion") int selectedEmotion,
            //@RequestParam(value = "file_1", required = false) MultipartFile[] file1,
            //@RequestParam(value = "file_2", required = false) MultipartFile[] file2,
            //@RequestParam(value = "file_3", required = false) MultipartFile[] file3,
            //@RequestParam(value = "file_4", required = false) MultipartFile[] file4,
            //@RequestParam(value = "file_5", required = false) MultipartFile[] file5,
            //@RequestParam(value = "file_6", required = false) MultipartFile[] file6,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestHeader("Authorization") String tokenHeader
    )
    {
        //==================Authorization==================
        Member authMember = mainService.checkUser(tokenHeader);

        if(authMember == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("id 토큰이 일치하지 않습니다.");
        //==================Authorization==================

        Review review = new Review();
        review.setMemberId(authMember.getMemberId());
        review.setSelectedDate(selectedDate);
        review.setUploadedDate(uploadedDate);
        review.setHashtags(hashtags);
        review.setReviewTitle(reviewTitle);
        review.setReviewText(reviewText);
        review.setSelectedEmotion(selectedEmotion);

        int result = reviewService.addReview(review, files);

        if(result != 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("등록이 안됏읍니다");

        return ResponseEntity.ok().build();
    }

    //게시글 등록
    @GetMapping("/")
    public ResponseEntity<?> addReview(@RequestHeader("Authorization") String tokenHeader)
    {
        //==================Authorization==================
        Member authMember = mainService.checkUser(tokenHeader);

        if(authMember == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("id 토큰이 일치하지 않습니다.");
        //==================Authorization==================

        List<SelectReviewResponse> reviewList = reviewService.selectReviewByMemberId(authMember.getMemberId());

        if(reviewList == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰가 없거나 조회가 안됏읍니다");

        return ResponseEntity.ok(reviewList);
    }

    //게시글 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable int reviewId,
                                          @RequestHeader("Authorization") String tokenHeader)
    {
        //==================Authorization==================
        Member authMember = mainService.checkUser(tokenHeader);

        if(authMember == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("id 토큰이 일치하지 않습니다.");
        //==================Authorization==================

        int isDelete = reviewService.deleteReview(reviewId);
        if(isDelete != 1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제 실패!");

        System.out.println("reviewId :"+reviewId +" 삭제 성공");
        return ResponseEntity.ok().build();
    }
}
