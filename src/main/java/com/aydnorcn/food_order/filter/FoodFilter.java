package com.aydnorcn.food_order.filter;

import com.aydnorcn.food_order.entity.Category;
import com.aydnorcn.food_order.entity.Food;
import com.aydnorcn.food_order.entity.Restaurant;
import org.springframework.data.jpa.domain.Specification;

public class FoodFilter {

    public static Specification<Food> filter(Category category, Restaurant restaurant, Double minPrice, Double maxPrice) {
        return Specification.where(categoryEquals(category))
                .and(restaurantEquals(restaurant))
                .and(priceGreaterThanOrEquals(minPrice))
                .and(priceLessThanOrEquals(maxPrice));
    }

    private static Specification<Food> categoryEquals(Category category) {
        return (root, query, criteriaBuilder) -> category == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("category"), category);
    }

    private static Specification<Food> restaurantEquals(Restaurant restaurant) {
        return (root, query, criteriaBuilder) -> restaurant == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("restaurant"), restaurant);
    }

    private static Specification<Food> priceGreaterThanOrEquals(Double minPrice) {
        return (root, query, criteriaBuilder) -> minPrice == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Food> priceLessThanOrEquals(Double maxPrice) {
        return (root, query, criteriaBuilder) -> maxPrice == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
