package com.nicholassr.product_service.mapper;

import com.nicholassr.product_service.dto.ProductDto;
import com.nicholassr.product_service.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
}
