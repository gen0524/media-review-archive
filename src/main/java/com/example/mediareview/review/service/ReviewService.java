package com.example.mediareview.review.service;

import com.example.mediareview.review.dto.ReviewRequest;
import com.example.mediareview.review.dto.ReviewResponse;
import com.example.mediareview.review.entity.Review;
import com.example.mediareview.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponse createReview(ReviewRequest request) {
        Review review = new Review(
                request.getTitle(),
                request.getCategory(),
                request.getRating(),
                request.getContent()
        );

        Review savedReview = reviewRepository.save(review);
        return new ReviewResponse(savedReview);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        return new ReviewResponse(review);
    }

    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        review.update(
                request.getTitle(),
                request.getCategory(),
                request.getRating(),
                request.getContent()
        );

        return new ReviewResponse(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}