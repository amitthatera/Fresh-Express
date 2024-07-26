package com.store.grocery.fresh_express.controller;

import com.store.grocery.fresh_express.dto.CategoryDTO;
import com.store.grocery.fresh_express.dto.PageableResponse;
import com.store.grocery.fresh_express.dto.RequestResponse;
import com.store.grocery.fresh_express.service.CategoryService;
import com.store.grocery.fresh_express.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping(value = "/", consumes = "multipart/form-data")
    public ResponseEntity<RequestResponse> createCategory(@RequestPart CategoryDTO categoryData,
                                                          @RequestPart MultipartFile categoryImage) {
        categoryService.createCategory(categoryData, categoryImage);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .message("Category Created!!")
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping(value = "/{categoryId}", consumes = "multipart/form-data")
    public ResponseEntity<RequestResponse> updateCategory(@PathVariable long categoryId,
                                                          @RequestPart CategoryDTO categoryData,
                                                          @RequestPart MultipartFile categoryImage) {
        categoryService.updateCategory(categoryId, categoryData, categoryImage);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Category Updated!!")
                        .build());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<RequestResponse> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Category Deleted Successfully!!!")
                        .build());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<RequestResponse> getCategoryById(@PathVariable long categoryId) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(categoryDTO)
                        .build());
    }

    @GetMapping()
    public ResponseEntity<RequestResponse> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.CATEGORY_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PageableResponse<CategoryDTO> categories = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(categories)
                        .build());
    }
}
