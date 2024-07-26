package com.store.grocery.fresh_express.service;

import com.store.grocery.fresh_express.dto.CategoryDTO;
import com.store.grocery.fresh_express.dto.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO, MultipartFile categoryImage);

    CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDTO, MultipartFile categoryImage);

    void deleteCategory(long categoryId);

    CategoryDTO getCategoryById(long categoryId);

    PageableResponse<CategoryDTO> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);

}
