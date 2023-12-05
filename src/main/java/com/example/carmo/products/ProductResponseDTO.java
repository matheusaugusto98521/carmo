package com.example.carmo.products;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private String message;
    private ProductModel product;

    public ProductResponseDTO(String message, ProductModel product){
        this.message = message;
        this.product = product;
    }
    
}
