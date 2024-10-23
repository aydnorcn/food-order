package com.aydnorcn.food_order.dto.food;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PatchFoodRequestDto {

    private String name;
    private String description;
    private Double price;
    private MultipartFile image;
    private Long categoryId;
}
