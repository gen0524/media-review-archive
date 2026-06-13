package com.example.mediareview.review.dto;

import com.example.mediareview.review.entity.Review;
import com.example.mediareview.review.enums.ReviewStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ReviewResponse {

    private Long id;
    private String title;
    private String category;
    private Integer rating;
    private String content;
    private String username;

    private ReviewStatus status;
    private LocalDate watchedDate;
    private String shortReview;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.category = review.getCategory();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.username = review.getUsername();

        this.status = review.getStatus();
        this.watchedDate = review.getWatchedDate();
        this.shortReview = review.getShortReview();

        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }
}