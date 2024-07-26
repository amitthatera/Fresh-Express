package com.store.grocery.fresh_express.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.store.grocery.fresh_express.model.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record CategoryDTO(
        @NotBlank(message = "Category UUID is required!!") String categoryUUID,
        @NotBlank(message = "Category name is required!!") String categoryName,
        String categoryDescription,
        @NotNull(message = "Please Upload Image!!") Image image,
        @JsonIgnore
        Optional<List<SubCategoryDTO>> subCategoryDTOS
) {
}
