package com.store.grocery.fresh_express.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryDTO(
        @NotBlank(message = "Category UUID is required!!") String categoryUUID,
        @NotBlank(message = "Category name is required!!") String categoryName,
        String categoryDescription,
        @JsonIgnore
        Optional<List<ProductDTO>> products
) {
}
