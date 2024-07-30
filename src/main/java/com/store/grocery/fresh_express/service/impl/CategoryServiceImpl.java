package com.store.grocery.fresh_express.service.impl;

import com.store.grocery.fresh_express.custom_exception.ApiException;
import com.store.grocery.fresh_express.custom_exception.ResourceNotFoundException;
import com.store.grocery.fresh_express.dto.CategoryDTO;
import com.store.grocery.fresh_express.dto.PageableResponse;
import com.store.grocery.fresh_express.mapper.CategoryMapper;
import com.store.grocery.fresh_express.model.Category;
import com.store.grocery.fresh_express.model.Image;
import com.store.grocery.fresh_express.repository.CategoryRepository;
import com.store.grocery.fresh_express.service.CategoryService;
import com.store.grocery.fresh_express.service.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final FileService fileService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
                               FileService fileService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.fileService = fileService;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO, MultipartFile categoryImage) {
        Category category = categoryMapper.mapToEntity(categoryDTO);
        boolean exist = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName()).isPresent();
        if (exist) {
            throw new ApiException("Category Already Exist!!");
        }

        Image image = fileService.uploadFile(categoryImage, "category");
        category.setCategoryUUID(UUID.randomUUID().toString().substring(0, 12).toUpperCase());
        category.setCategoryImage(image);
        Category newCategory = categoryRepository.save(category);
        return categoryMapper.mapToDTO(newCategory);
    }

    @Override
    public CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDTO, MultipartFile categoryImage) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Exist!!"));
        if(categoryImage != null){
            fileService.deleteImage(category.getCategoryImage().getImageName(), "category");
            Image image = fileService.uploadFile(categoryImage, "category");
            category.setCategoryImage(image);
        }
        category.setCategoryName(categoryDTO.categoryName());
        category.setCategoryDescription(categoryDTO.categoryDescription());
        Category upatedCategory = categoryRepository.save(category);
        return categoryMapper.mapToDTO(upatedCategory);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Exist!!"));
        fileService.deleteImage(category.getCategoryImage().getImageName(), "category");
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
