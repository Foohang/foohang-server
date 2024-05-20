package com.jjh.foohang.review.dto;

import lombok.Data;

import java.util.List;

@Data
public class SelectReviewResponse {
    private String          title;
    private String          postDate;
    private String          travelDate;
    private List<String>    images;
    private String          content;
    private List<String>    hashtags;
    private int             selectedEmotion;
}
