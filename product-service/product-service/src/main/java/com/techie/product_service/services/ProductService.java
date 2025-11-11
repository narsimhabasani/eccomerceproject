package com.techie.product_service.services;

import com.techie.product_service.dtos.ProductRequest;
import com.techie.product_service.dtos.ProductResponse;
import com.techie.product_service.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService
{

     ProductResponse createProduct(ProductRequest productRequest);

     List<ProductResponse> getAllProducts();
}
