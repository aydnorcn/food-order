package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.repository.CategoryRepository;
import com.aydnorcn.food_order.service.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryValidationService {

    private final CategoryRepository categoryRepository;
    private final UserContextService userContextService;

    public void validateCategoryName(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Category already exists!");
        }
    }

    public void validateAuthority() {
        if (!userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()) {
            throw new NoAuthorityException("You are not authorized to create a category!");
        }
    }
}