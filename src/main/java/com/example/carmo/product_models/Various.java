package com.example.carmo.product_models;

import com.example.carmo.products.Product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("various")
public class Various extends Product {
    
}
