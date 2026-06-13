package com.example.mediareview.review.entity;

import com.example.mediareview.review.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status;

    @Column(nullable = false)
    private String username;

    private LocalDate watchedDate;

    private String shortReview;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review(
            String title,
            String category,
            Integer rating,
            String content,
            ReviewStatus status,
            LocalDate watchedDate,
            String shortReview,
            String username
    ) {
        this.title = title;
        this.category = category;
        this.rating = rating;
        this.content = content;
        this.status = status;
        this.watchedDate = watchedDate;
        this.shortReview = shortReview;
        this.username = username;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = ReviewStatus.COMPLETED;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void update(
            String title,
            String category,
            Integer rating,
            String content,
            ReviewStatus status,
            LocalDate watchedDate,
            String shortReview
    ) {
        this.title = title;
        this.category = category;
        this.rating = rating;
        this.content = content;
        this.status = status;
        this.watchedDate = watchedDate;
        this.shortReview = shortReview;
    }
}