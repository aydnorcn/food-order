package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.entity.User;
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

    public void validateAuthority(String action) {
        User currentUser = userContextService.getCurrentAuthenticatedUser();
        if (!userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()) {
            throw new NoAuthorityException("User with ID " + currentUser.getId() + " is not authorized to " + action);
        }
    }
}