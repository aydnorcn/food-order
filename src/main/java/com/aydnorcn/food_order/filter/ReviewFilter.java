package com.aydnorcn.food_order.filter;

import com.aydnorcn.food_order.entity.Order;
import com.aydnorcn.food_order.entity.Review;
import org.springframework.data.jpa.domain.Specification;

public class ReviewFilter {

    public static Specification<Review> filter(Order order, int minRating, int maxRating) {
        return Specification.where(orderEquals(order))
                .and(ratingGreaterThanOrEquals(minRating))
                .and(ratingLessThanOrEquals(maxRating));
    }

    private static Specification<Review> orderEquals(Order order) {
        return (root, query, criteriaBuilder) -> order == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("order"), order);
    }

    private static Specification<Review> ratingGreaterThanOrEquals(int minRating) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating);
    }

    private static Specification<Review> ratingLessThanOrEquals(int maxRating) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating);
    }
}
