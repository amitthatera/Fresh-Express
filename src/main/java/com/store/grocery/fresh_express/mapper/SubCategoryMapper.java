package com.store.grocery.fresh_express.mapper;

import com.store.grocery.fresh_express.dto.SubCategoryDTO;
import com.store.grocery.fresh_express.model.SubCategory;
import com.store.grocery.fresh_express.shared.kernel.Mapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubCategoryMapper implements Mapper<SubCategory, SubCategoryDTO> {

    private final CategoryMapper categoryMapper;

    public SubCategoryMapper(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }

    @Override
    public SubCategory mapToEntity(SubCategoryDTO dto) {
        return SubCategory.builder()
                .subCategoryName(dto.subCategoryName())
                .subCategoryDescription(dto.subCategoryDescription())
                .subCategoryImage(dto.image())
                .build();
    }

    @Override
    public SubCategoryDTO mapToDTO(SubCategory entity) {
        return new SubCategoryDTO(entity.getSubCategoryUUID(), entity.getSubCategoryName(),
                entity.getSubCategoryDescription(), entity.getSubCategoryImage(), categoryMapper.mapToDTO(entity.getCategory()),
                Optional.empty());
    }
}
