package com.store.grocery.fresh_express.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.store.grocery.fresh_express.model.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SubCategoryDTO(
        @NotBlank(message = "Category UUID is required!!") String subCategoryUUID,
        @NotBlank(message = "Category name is required!!") String subCategoryName,
        String subCategoryDescription,
        @NotNull(message = "Please Upload Image!!") Image image,
        CategoryDTO categoryDTO,
        @JsonIgnore
        Optional<List<ProductDTO>> products
) {
}
