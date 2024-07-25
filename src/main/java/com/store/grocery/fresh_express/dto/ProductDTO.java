package com.store.grocery.fresh_express.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.store.grocery.fresh_express.model.Image;
import jakarta.validation.constraints.*;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDTO(
        @NotBlank(message = "Product name is required!!") String productName,
        String productDescription,
        @NotBlank(message = "Product UUID is required!!")  String productUUID,
        @NotNull(message = "Product price is required!!") @Positive(message = "Product price must be positive!!") double productPrice,
        double discount,
        double discountedPrice,
        boolean isAvailable,
        @NotNull(message = "Stock is required!!") @Min(value = 0, message = "Stock cannot be negative!!") int stock,
        @NotEmpty(message = "Product images cannot be empty!!") Set<Image> productImages,
        SubCategoryDTO subCategory
) {
}
