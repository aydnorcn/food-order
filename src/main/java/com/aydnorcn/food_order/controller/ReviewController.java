package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.review.CreateReviewRequestDto;
import com.aydnorcn.food_order.entity.Review;
import com.aydnorcn.food_order.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @GetMapping("/reviews")
    public ResponseEntity<PageResponseDto<Review>> getReviews(@RequestParam(name = "page-no", defaultValue = "0") int pageNo,
                                              @RequestParam(name = "page-size", defaultValue = "10") int pageSize){
        return ResponseEntity.ok(reviewService.getReviews(pageNo, pageSize));
    }

    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<PageResponseDto<Review>> getUserReviews(@PathVariable String userId,
                                                  @RequestParam(name = "page-no", defaultValue = "0") int pageNo,
                                                  @RequestParam(name = "page-size", defaultValue = "10") int pageSize){
        return ResponseEntity.ok(reviewService.getUserReviews(userId, pageNo, pageSize));
    }

    @PostMapping("/orders/{orderId}/reviews")
    public ResponseEntity<Review> createReview(@PathVariable Long orderId, @Valid @RequestBody CreateReviewRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(orderId, dto));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

}