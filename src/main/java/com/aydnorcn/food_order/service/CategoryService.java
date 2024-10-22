package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.category.CreateCategoryRequestDto;
import com.aydnorcn.food_order.entity.Category;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.CategoryRepository;
import com.aydnorcn.food_order.service.validation.CategoryValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidationService categoryValidationService;

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

        categoryValidationService.validateAuthority();

        Category category = new Category();
        category.setName(dto.getName());

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, CreateCategoryRequestDto dto) {
        Category category = getCategoryById(categoryId);

        categoryValidationService.validateCategoryName(dto.getName());

        categoryValidationService.validateAuthority();

        category.setName(dto.getName());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);

        categoryValidationService.validateAuthority();

        categoryRepository.delete(category);
    }
}
