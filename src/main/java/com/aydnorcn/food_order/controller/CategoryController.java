package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.category.CreateCategoryRequestDto;
import com.aydnorcn.food_order.entity.Category;
import com.aydnorcn.food_order.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }


    @GetMapping
    public ResponseEntity<PageResponseDto<Category>> getCategories(@RequestParam(name = "page-no", defaultValue = "0", required = false) int pageNo,
                                                                   @RequestParam(name = "page-size", defaultValue = "10", required = false) int pageSize,
                                                                   @RequestParam(name = "sort-by", defaultValue = "id", required = false) String sortBy,
                                                                   @RequestParam(name = "sort-dir", defaultValue = "asc", required = false) String sortDirection) {
        return ResponseEntity.ok(categoryService.getCategories(pageNo, pageSize, sortBy, sortDirection));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(dto));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CreateCategoryRequestDto dto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, dto));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}