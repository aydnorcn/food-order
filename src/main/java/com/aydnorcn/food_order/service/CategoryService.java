package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.category.CreateCategoryRequestDto;
import com.aydnorcn.food_order.entity.Category;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.CategoryRepository;
import com.aydnorcn.food_order.service.validation.CategoryValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidationService categoryValidationService;
    private final UserContextService userContextService;

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    public PageResponseDto<Category> getCategories(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(pageNo, pageSize, sort));
        return new PageResponseDto<>(categories);
    }

    public Category createCategory(CreateCategoryRequestDto dto) {
        categoryValidationService.validateCategoryName(dto.getName());

        categoryValidationService.validateAuthority(String.format("create category with name %s", dto.getName()));

        Category category = new Category();
        category.setName(dto.getName());

        log.info("Category with name {} created by user with ID {}", dto.getName(), userContextService.getCurrentAuthenticatedUser().getId());

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, CreateCategoryRequestDto dto) {
        Category category = getCategoryById(categoryId);

        categoryValidationService.validateCategoryName(dto.getName());

        categoryValidationService.validateAuthority(String.format("update category with ID %s", categoryId));

        log.info("Category with ID {} updated from name {} to {} by user with ID {}", categoryId, category.getName(), dto.getName(), userContextService.getCurrentAuthenticatedUser().getId());

        category.setName(dto.getName());


        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);

        categoryValidationService.validateAuthority(String.format("delete category with ID %s", categoryId));

        categoryRepository.delete(category);

        log.info("Category with ID {} and name {} deleted by user with ID {}", categoryId, category.getName(), userContextService.getCurrentAuthenticatedUser().getId());
    }
}
