package com.example.carmo.dto;

import com.example.carmo.products.Product;

import lombok.Data;

@Data
public class CarItemDTO {

    private Long id;
    private Product product;
    private int quantity;
    
}
