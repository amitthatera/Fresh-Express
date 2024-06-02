package com.store.grocery.fresh_express.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CategoryDTO(
        @NotBlank(message = "Category UUID is required!!") String categoryUUID,
        @NotBlank(message = "Category name is required!!") String categoryName,
        String categoryDescription,
        List<ProductDTO> products
) {
}
