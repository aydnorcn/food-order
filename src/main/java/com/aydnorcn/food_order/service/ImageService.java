package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.entity.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final Path rootLocation = Paths.get("uploads");

    public byte[] getImage(String imageUrl) {
        try {
            return Files.readAllBytes(Paths.get(imageUrl));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get image", e);
        }
    }

    public String saveImage(MultipartFile image) {
        validateImage(image);

        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectories(rootLocation);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create upload directory", e);
            }
        }

        try {
            if (image != null && !image.isEmpty()) {
                String extension = image.getContentType().substring(6);
                UUID id = UUID.randomUUID();
                String imageUrl = id.toString() + "." + extension;
                Files.copy(image.getInputStream(), this.rootLocation.resolve(imageUrl));
                return this.rootLocation.resolve(imageUrl).toString();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + image.getOriginalFilename(), e);
        }
        return null;
    }

    public void deleteImage(String imageUrl) {
        try {
            Files.deleteIfExists(Paths.get(imageUrl));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image", e);
        }
    }

    public void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty() || image.getContentType() == null ||
            (!image.getContentType().equals("image/png") && !image.getContentType().equals("image/jpeg"))) {
            throw new IllegalArgumentException("Image should not be null");
        }
    }
}
