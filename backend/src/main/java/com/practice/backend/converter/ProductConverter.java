package com.practice.backend.converter;

import com.practice.backend.dto.ProductRequest;
import com.practice.backend.dto.ProductResponse;
import com.practice.backend.model.entity.Category;
import com.practice.backend.model.entity.Product;
import com.practice.backend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductConverter implements Converter<Product, ProductRequest, ProductResponse> {
    private final CategoryService categoryService;

    @Override
    public Product requestToEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        Category existingCategory = categoryService.getOneByName(productRequest.getCategoryName());
        product.setCategory(existingCategory);
        product.setImageName(productRequest.getImageName());
        return product;
    }

    @Override
    public ProductResponse entityToResponse(Product product) {
       return ProductResponse.builder()
               .id(product.getId())
               .name(product.getName())
               .description(product.getDescription())
               .price(product.getPrice())
               .category(product.getCategory())
               .imageName(product.getImageName())
               .build();
    }

    @Override
    public List<ProductResponse> entityToResponse(List<Product> entity) {
        return entity.stream().map(this::entityToResponse).toList();
    }
}
