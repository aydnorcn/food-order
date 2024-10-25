package com.aydnorcn.food_order.dto.review;

import com.aydnorcn.food_order.dto.order.OrderResponseDto;
import com.aydnorcn.food_order.entity.Review;
import lombok.Data;

@Data
public class ReviewResponseDto {

    private Long id;
    private String comment;
    private int rating;
    private String userId;
    private OrderResponseDto order;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.userId = review.getUser().getId();
        this.order = new OrderResponseDto(review.getOrder());
    }
}