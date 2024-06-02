package com.store.grocery.fresh_express.dto;

import com.store.grocery.fresh_express.model.Image;
import jakarta.validation.constraints.*;

import java.util.Set;

public record ProductDTO(
        @NotBlank(message = "Product name is required!!") String productName,
        String productDescription,
        @NotBlank(message = "Product UUID is required!!")  String productUUID,
        @NotNull(message = "Product price is required!!") @Positive(message = "Product price must be positive!!") Double productPrice,
        Double discount,
        Double discountedPrice,
        Boolean isAvailable,
        @NotNull(message = "Stock is required!!") @Min(value = 0, message = "Stock cannot be negative!!") Integer stock,
        @NotEmpty(message = "Product images cannot be empty!!") Set<Image> productImages,
        CategoryDTO category,
        VendorDTO vendor
) {
}
