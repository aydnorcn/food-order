package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.review.CreateReviewRequestDto;
import com.aydnorcn.food_order.dto.review.ReviewResponseDto;
import com.aydnorcn.food_order.entity.Review;
import com.aydnorcn.food_order.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(new ReviewResponseDto(reviewService.getReviewById(reviewId)));
    }

    @GetMapping("/reviews")
    public ResponseEntity<PageResponseDto<ReviewResponseDto>> getReviews(@RequestParam(name = "order-id", required = false) Long orderId,
                                                                         @RequestParam(name = "min-rating", defaultValue = "0") int minRating,
                                                                         @RequestParam(name = "max-rating", defaultValue = "5") int maxRating,
                                                                         @RequestParam(name = "page-no", defaultValue = "0") int pageNo,
                                                                         @RequestParam(name = "page-size", defaultValue = "10") int pageSize,
                                                                         @RequestParam(name = "sort-by", defaultValue = "id", required = false) String sortBy,
                                                                         @RequestParam(name = "sort-dir", defaultValue = "asc", required = false) String sortDirection) {
        PageResponseDto<Review> reviews = reviewService.getReviews(orderId, minRating, maxRating, pageNo, pageSize, sortBy, sortDirection);
        List<ReviewResponseDto> reviewResponses = reviews.getContent().stream().map(ReviewResponseDto::new).toList();
        return ResponseEntity.ok(new PageResponseDto<>(reviewResponses, reviews.getPageNo(), reviews.getPageSize(), reviews.getTotalElements(), reviews.getTotalPages()));
    }

    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<PageResponseDto<ReviewResponseDto>> getUserReviews(@PathVariable String userId,
                                                                             @RequestParam(name = "order-id", required = false) Long orderId,
                                                                             @RequestParam(name = "min-rating", defaultValue = "0") int minRating,
                                                                             @RequestParam(name = "max-rating", defaultValue = "5") int maxRating,
                                                                             @RequestParam(name = "page-no", defaultValue = "0") int pageNo,
                                                                             @RequestParam(name = "page-size", defaultValue = "10") int pageSize,
                                                                             @RequestParam(name = "sort-by", defaultValue = "id", required = false) String sortBy,
                                                                             @RequestParam(name = "sort-dir", defaultValue = "asc", required = false) String sortDirection) {
        PageResponseDto<Review> reviews = reviewService.getUserReviews(userId, orderId, minRating, maxRating, pageNo, pageSize, sortBy, sortDirection);
        List<ReviewResponseDto> reviewResponses = reviews.getContent().stream().map(ReviewResponseDto::new).toList();
        return ResponseEntity.ok(new PageResponseDto<>(reviewResponses, reviews.getPageNo(), reviews.getPageSize(), reviews.getTotalElements(), reviews.getTotalPages()));
    }

    @PostMapping("/orders/{orderId}/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable Long orderId, @Valid @RequestBody CreateReviewRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReviewResponseDto(reviewService.createReview(orderId, dto)));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

}