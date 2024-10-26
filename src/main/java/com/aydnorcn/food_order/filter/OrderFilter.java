package com.aydnorcn.food_order.filter;

import com.aydnorcn.food_order.entity.Order;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.utils.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

public class OrderFilter {

    public static Specification<Order> filter(User user, Double minPrice, Double maxPrice, String status){
        return Specification.where(priceGreaterThanOrEquals(minPrice))
                .and(userEquals(user))
                .and(priceLessThanOrEquals(maxPrice))
                .and(statusEquals(status));
    }

    private static Specification<Order> userEquals(User user) {
        return (root, query, criteriaBuilder) -> user == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("user"), user);
    }

    private static Specification<Order> priceGreaterThanOrEquals(Double minPrice) {
        return (root, query, criteriaBuilder) -> minPrice == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThanOrEqualTo(root.get("total"), minPrice);
    }

    private static Specification<Order> priceLessThanOrEquals(Double maxPrice) {
        return (root, query, criteriaBuilder) -> maxPrice == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThanOrEqualTo(root.get("total"), maxPrice);
    }

    private static Specification<Order> statusEquals(String status) {
        return (root, query, criteriaBuilder) -> status == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("status"), OrderStatus.fromString(status));
    }
}
