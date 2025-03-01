package com.practice.backend.api.v1.controller;

import com.practice.backend.api.v1.common.AbstractController;
import com.practice.backend.api.v1.exception.PatchFieldConstraintViolationException;
import com.practice.backend.converter.BaseConverter;
import com.practice.backend.converter.ProductConverter;
import com.practice.backend.dto.ProductRequest;
import com.practice.backend.dto.ProductResponse;
import com.practice.backend.filtering.ProductSpecificationBuilder;
import com.practice.backend.filtering.specification.EntityFilterSpecificationBuilder;
import com.practice.backend.model.entity.Category;
import com.practice.backend.model.entity.Product;
import com.practice.backend.service.AbstractService;
import com.practice.backend.service.CategoryService;
import com.practice.backend.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.practice.backend.api.v1.ApiConstants.PRODUCT_PATH;

@RestController
@RequestMapping(PRODUCT_PATH)
public class ProductController extends AbstractController<Product, ProductRequest, ProductResponse> {

    private final ProductSpecificationBuilder specificationBuilder;
    private final ProductService service;
    private final CategoryService categoryService;
    private final ProductConverter converter;
    private final Validator validator;

    public ProductController(ProductSpecificationBuilder specificationBuilder, ProductService service, CategoryService categoryService, ProductConverter converter, ValidatorFactory validatorFactory) {
        this.specificationBuilder = specificationBuilder;
        this.service = service;
        this.categoryService = categoryService;
        this.converter = converter;
        this.validator = validatorFactory.getValidator();
    }

    @Override
    protected Product executeEntityCreate(Product product, ProductRequest request) {
        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setPrice(product.getPrice());
        product.setCategory(product.getCategory());
        return super.executeEntityCreate(product, request);
    }

    @Override
    protected Product executeEntityUpdate(Product entity, ProductRequest request) {
        return super.executeEntityUpdate(entity, request);
    }

    @Override
    public AbstractService<Product> getService() {
        return this.service;
    }

    @Override
    public BaseConverter<Product, ProductRequest, ProductResponse> getConverter() {
        return this.converter;
    }

    @Override
    public ProductResponse convertEntityToResponse(Product product, List<String> entitiesToExpand) {
        return getConverter().entityToResponse(product);
    }

    @Override
    public Class<Product> getEntityClass() {
        return Product.class;
    }

    @Override
    protected Product updateEntity(ProductRequest request, Product product) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        Category existingCategory = categoryService.getOneByName(product.getCategory().getName());
        product.setCategory(existingCategory);
        product.setImageName(request.getImageName());
        return product;
    }

    @Override
    public EntityFilterSpecificationBuilder<Product> getFilterSpecificationsBuilder() {
        return specificationBuilder;
    }

    @Override
    protected void patchFields(Product entity, Map<String, Object> request) {
        request.entrySet().forEach(requestedField -> patchField(entity, requestedField));
    }

    protected void patchField(Product entity, Map.Entry<String, Object> requestedField) {
        switch (requestedField.getKey()) {
            case "name":
                String name = conversionService.convert(requestedField.getValue(), String.class);
                validatePatchField("name", name);
                entity.setName(name);
                return;
            case "description":
                String description = conversionService.convert(requestedField.getValue(), String.class);
                validatePatchField("description", description);
                entity.setDescription(description);
                return;
            case "price":
                String priceString = conversionService.convert(requestedField.getValue(), String.class);
                validatePatchField("price", priceString);
                assert priceString != null;
                BigDecimal price = new BigDecimal(priceString);
                entity.setPrice(price);
                return;
            case "imageName":
                String imageName = conversionService.convert(requestedField.getValue(), String.class);
                validatePatchField("imageName", imageName);
                entity.setImageName(imageName);
                return;
            case "categoryName":
                String categoryName = conversionService.convert(requestedField.getValue(), String.class);
                validatePatchField("categoryName", categoryName);
                Category existingCategory = categoryService.getOneByName(categoryName);
                entity.setCategory(existingCategory);
        }
    }

    private void validatePatchField(String propertyName, Object value) {
        Set<ConstraintViolation<ProductRequest>> validationResult = validator
                .validateValue(ProductRequest.class, propertyName, value);
        if (validationResult.isEmpty()) {
            return;
        }
        throw new PatchFieldConstraintViolationException(validationResult);
    }

}