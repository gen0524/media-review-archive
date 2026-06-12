package com.example.mediareview.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequest {

    private String title;
    private String category;
    private Integer rating;
    private String content;
}