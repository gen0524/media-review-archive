package com.example.mediareview.review.dto;

import com.example.mediareview.review.enums.ReviewStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReviewRequest {

    private String title;
    private String category;
    private Integer rating;
    private String content;
    private String username;

    private ReviewStatus status;    // WANT_TO_WATCH, WATCHING, COMPLETED
    private LocalDate watchedDate;  // 감상일
    private String shortReview;     // 한줄평
}