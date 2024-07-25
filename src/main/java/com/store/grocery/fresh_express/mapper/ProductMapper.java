package com.store.grocery.fresh_express.mapper;

import com.store.grocery.fresh_express.dto.ProductDTO;
import com.store.grocery.fresh_express.model.Product;
import com.store.grocery.fresh_express.shared.kernel.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<Product, ProductDTO> {

    private final SubCategoryMapper categoryMapper;

    public ProductMapper(SubCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }


    @Override
    public Product mapToEntity(ProductDTO dto) {
        return Product.builder()
                .productName(dto.productName())
                .productDescription(dto.productDescription())
                .productPrice(dto.productPrice())
                .discount(dto.discount())
                .discountedPrice(dto.discountedPrice())
                .isAvailable(dto.isAvailable())
                .stock(dto.stock())
                .productImages(dto.productImages())
                .build();
    }

    @Override
    public ProductDTO mapToDTO(Product entity) {
        return new ProductDTO(entity.getProductName(), entity.getProductDescription(), entity.getProductUUID(),
                entity.getProductPrice(), entity.getDiscount(), entity.getDiscountedPrice(), entity.isAvailable(),
                entity.getStock(), entity.getProductImages(), categoryMapper.mapToDTO(entity.getSubCategory()));
    }
}
