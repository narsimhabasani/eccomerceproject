package com.techie.product_service.controllers;

import com.techie.product_service.dtos.ProductRequest;
import com.techie.product_service.dtos.ProductResponse;
import com.techie.product_service.entities.Product;
import com.techie.product_service.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@Slf4j
public class ProductController
{
    @Autowired
   private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {

             return new ResponseEntity<>(productService.createProduct(productRequest),HttpStatus.CREATED);


    }

    @GetMapping
    @Cacheable("products")
     List<ProductResponse> getAllProducts()
    {
       return productService.getAllProducts();
    }
}
