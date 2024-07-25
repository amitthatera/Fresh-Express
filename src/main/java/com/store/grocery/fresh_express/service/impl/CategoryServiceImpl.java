package com.store.grocery.fresh_express.service.impl;

import com.store.grocery.fresh_express.custom_exception.ApiException;
import com.store.grocery.fresh_express.custom_exception.ResourceNotFoundException;
import com.store.grocery.fresh_express.dto.CategoryDTO;
import com.store.grocery.fresh_express.dto.PageableResponse;
import com.store.grocery.fresh_express.mapper.CategoryMapper;
import com.store.grocery.fresh_express.model.Category;
import com.store.grocery.fresh_express.repository.CategoryRepository;
import com.store.grocery.fresh_express.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.mapToEntity(categoryDTO);
        boolean exist = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName()).isPresent();
        if(exist){
            throw new ApiException("Category Already Exist!!");
        }
        category.setCategoryUUID(UUID.randomUUID().toString().substring(0, 9).toUpperCase());
        Category newCategory = categoryRepository.save(category);
        return categoryMapper.mapToDTO(newCategory);
    }

    @Override
    public CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Exist!!"));
        category.setCategoryName(categoryDTO.categoryName());
        category.setCategoryDescription(categoryDTO.categoryDescription());
         Category upatedCategory = categoryRepository.save(category);
        return categoryMapper.mapToDTO(upatedCategory);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Exist!!"));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategoryById(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Exist!!"));
        return categoryMapper.mapToDTO(category);
    }

    @Override
    public PageableResponse<CategoryDTO> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        return PageableResponse.getPageableResponse(page, categoryMapper);
    }
}
