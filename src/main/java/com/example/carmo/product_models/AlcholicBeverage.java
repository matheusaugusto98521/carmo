package com.example.carmo.product_models;

import com.example.carmo.products.Product;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("alcoholic")
public class AlcholicBeverage extends Product {
    
    private double alcPercent;

    @Column(nullable = true)
    private String origin;

    
}
