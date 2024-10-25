package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.review.CreateReviewRequestDto;
import com.aydnorcn.food_order.entity.Order;
import com.aydnorcn.food_order.entity.Review;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.ReviewRepository;
import com.aydnorcn.food_order.service.validation.ReviewValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserContextService userContextService;
    private final UserService userService;
    private final OrderService orderService;
    private final ReviewValidationService reviewValidationService;

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review not found!"));
    }

    public PageResponseDto<Review> getReviews(int pageNo, int pageSize) {
        User user = userContextService.getCurrentAuthenticatedUser();
        return new PageResponseDto<>(reviewRepository.findAllByUser(user, PageRequest.of(pageNo, pageSize)));
    }

    public PageResponseDto<Review> getUserReviews(String userId, int pageNo, int pageSize) {
        User user = userService.getUserById(userId);

        reviewValidationService.validateAuthority(user);

        return new PageResponseDto<>(reviewRepository.findAllByUser(user, PageRequest.of(pageNo, pageSize)));
    }

    public Review createReview(Long orderId, CreateReviewRequestDto dto) {
        Order order = orderService.getOrderById(orderId);
        User user = userContextService.getCurrentAuthenticatedUser();

        if(reviewRepository.existsByOrder(order)) {
            throw new ResourceNotFoundException("Review already exists!");
        }

        reviewValidationService.validateAuthority(order.getUser());

        Review review = new Review();
        review.setUser(user);
        review.setOrder(order);
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        Review review = getReviewById(reviewId);

        reviewValidationService.validateAuthority(review.getUser());

        reviewRepository.delete(review);
    }
}