package com.nicholassr.product_service.service;

import com.nicholassr.product_service.dto.ProductDto;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long id);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
    Page<ProductDto> getAllProducts(Pageable pageable);
}
