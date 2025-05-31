package com.nicholassr.product_service.controller;


import com.nicholassr.product_service.dto.ProductDto;
import com.nicholassr.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/${app.api.version}/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody @Valid ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        Map<String, Object> response = new HashMap<>();
        response.put("data", toJsonApi(createdProduct));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("data", toJsonApi(productDto));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        Map<String, Object> response = new HashMap<>();
        response.put("data", toJsonApi(updatedProduct));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.getAllProducts((Pageable) pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("data", products.getContent());
        response.put("meta", Map.of(
                "total", products.getTotalElements(),
                "totalPages", products.getTotalPages()
        ));
        return ResponseEntity.ok(response);
    }

    // Utiliza un mapa para representar el modelo JSON:API
    private Map<String, Object> toJsonApi(ProductDto productDto) {
        Map<String, Object> model = new HashMap<>();
        model.put("id", productDto.getId());
        model.put("type", "product");
        model.put("attributes", Map.of(
                "name", productDto.getName(),
                "price", productDto.getPrice(),
                "createdAt", productDto.getCreatedAt(),
                "updatedAt", productDto.getUpdatedAt()
        ));
        return model;
    }
}