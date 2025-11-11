package com.techie.product_service.services;

import com.techie.product_service.ProductServiceApplication;
import com.techie.product_service.dtos.ProductRequest;
import com.techie.product_service.dtos.ProductResponse;
import com.techie.product_service.entities.Product;
import com.techie.product_service.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductServiceImpl implements ProductService
{
@Autowired
private ProductRepository productRepository;
    @Override
    public ProductResponse createProduct(ProductRequest productRequest)
    {
        Product product = Product.builder().name(productRequest.getName())
                .description(productRequest.getDescription()).
                price(productRequest.getPrice()).build();

        Product savedProduct =productRepository.save(product);
        log.info("product {} is saved",product.getId());


        return ProductResponse.builder()
            .name(savedProduct.getName())
            .description(savedProduct.getDescription())
            .build();

    }

    @Override
    public List<ProductResponse> getAllProducts()
    {
       List<Product> products = productRepository.findAll();

       return products.stream().map(product -> mapToProductResponse(product)).toList();


    }

    private ProductResponse mapToProductResponse(Product product)
    {
       return ProductResponse.builder().name(product.getName())
                .description(product.getDescription()).build();

    }
}
