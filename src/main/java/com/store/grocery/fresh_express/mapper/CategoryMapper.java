package com.store.grocery.fresh_express.mapper;

import com.store.grocery.fresh_express.dto.CategoryDTO;
import com.store.grocery.fresh_express.model.Category;
import com.store.grocery.fresh_express.shared.kernel.Mapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryMapper implements Mapper<Category, CategoryDTO> {

    @Override
    public Category mapToEntity(CategoryDTO dto) {
        return Category.builder()
                .categoryName(dto.categoryName())
                .categoryDescription(dto.categoryDescription())
                .categoryImage(dto.image())
                .build();
    }

    @Override
    public CategoryDTO mapToDTO(Category entity) {
        return new CategoryDTO(entity.getCategoryUUID(), entity.getCategoryName(), entity.getCategoryDescription(),
                entity.getCategoryImage(),Optional.empty());
    }
}
