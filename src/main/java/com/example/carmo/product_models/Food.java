package com.example.carmo.product_models;

import com.example.carmo.products.Product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("food")
public class Food extends Product {
    
}
