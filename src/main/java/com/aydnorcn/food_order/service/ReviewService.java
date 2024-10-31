package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.review.CreateReviewRequestDto;
import com.aydnorcn.food_order.entity.Order;
import com.aydnorcn.food_order.entity.Review;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.filter.ReviewFilter;
import com.aydnorcn.food_order.repository.ReviewRepository;
import com.aydnorcn.food_order.service.validation.ReviewValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review not found!"));

        reviewValidationService.validateAuthority(review.getUser(), String.format("view review with ID %s", reviewId));

        return review;
    }

    public PageResponseDto<Review> getReviews(Long orderId, int minRating, int maxRating, int pageNo, int pageSize, String sortBy, String sortDirection) {
        User user = userContextService.getCurrentAuthenticatedUser();

        Order order = (orderId != null) ? orderService.getOrderById(orderId) : null;

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Specification<Review> specification = ReviewFilter.filter(order, minRating, maxRating);

        return new PageResponseDto<>(reviewRepository.findAllByUser(user, specification,PageRequest.of(pageNo, pageSize, sort)));
    }

    public PageResponseDto<Review> getUserReviews(String userId, Long orderId, int minRating, int maxRating, int pageNo, int pageSize, String sortBy, String sortDirection) {
        User user = userService.getUserById(userId);

        reviewValidationService.validateAuthority(user, String.format("view reviews of user with ID %s", userId));

        Order order = (orderId != null) ? orderService.getOrderById(orderId) : null;

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Specification<Review> specification = ReviewFilter.filter(order, minRating, maxRating);

        return new PageResponseDto<>(reviewRepository.findAllByUser(user, specification, PageRequest.of(pageNo, pageSize, sort)));
    }

    public Review createReview(Long orderId, CreateReviewRequestDto dto) {
        Order order = orderService.getOrderById(orderId);
        User user = userContextService.getCurrentAuthenticatedUser();

        reviewValidationService.validateAuthority(order.getUser(), String.format("create review for order with ID %s", orderId));
        reviewValidationService.validateReviewCreation(order);

        Review review = new Review();
        review.setUser(user);
        review.setOrder(order);
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        Review review = getReviewById(reviewId);

        reviewValidationService.validateAuthority(review.getUser(), String.format("delete review with ID %s", reviewId));

        reviewRepository.delete(review);
    }
}