package com.jjh.foohang.review.controller;

import com.jjh.foohang.main.jwtUtil.JWTUtil;
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
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

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
        //인증
        int idToken = jwtUtil.getMemberIdFromToken(tokenHeader.substring(7));

        int memberId = memberService.findMemberById(idToken).getMemberId();

        if(memberId != idToken)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("id 토큰이 일치하지 않습니다.");

        Review review = new Review();
        review.setMemberId(memberId);
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


    @GetMapping("/")
    public ResponseEntity<?> addReview(@RequestHeader("Authorization") String tokenHeader)
    {
        //인증
        int idToken = jwtUtil.getMemberIdFromToken(tokenHeader.substring(7));

        int memberId = memberService.findMemberById(idToken).getMemberId();

        if(memberId != idToken)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("id 토큰이 일치하지 않습니다.");

        List<SelectReviewResponse> reviewList = reviewService.selectReviewByMemberId(memberId);

        if(reviewList == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰가 없거나 조회가 안됏읍니다");

        return ResponseEntity.ok(reviewList);
    }
}
