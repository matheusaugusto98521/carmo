package com.example.carmo.dto;

import com.example.carmo.products.Product;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private String message;
    private Product product;

    public ProductResponseDTO(String message, Product product){
        this.message = message;
        this.product = product;
    }
    
}
