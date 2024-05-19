package com.jjh.foohang.review.controller;

import com.jjh.foohang.main.jwtUtil.JWTUtil;
import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.service.MemberService;
import com.jjh.foohang.review.dto.Review;
import com.jjh.foohang.review.model.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    //게시글 등록
    @PostMapping("/")
    public ResponseEntity<?> addReview(
            @RequestPart("selectedDate") String selectedDate,
            @RequestPart("uplodedDate") String uplodedDate,
            @RequestPart("hashtags") String hashtags,
            @RequestPart("reviewTitle") String reviewTitle,
            @RequestPart("reviewText") String reviewText,
            @RequestPart("selectedEmotion") int selectedEmotion,
            @RequestPart(value = "files", required = false) MultipartFile[] files,
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
        review.setUplodedDate(uplodedDate);
        review.setHashtags(hashtags);
        review.setReviewTitle(reviewTitle);
        review.setReviewText(reviewText);
        review.setSelectedEmotion(selectedEmotion);

        int result = reviewService.addReview(review, files);

        if(result != 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("등록이 안됏읍니다");

        return ResponseEntity.ok().build();
    }

}
