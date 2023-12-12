package com.example.carmo.product_models;

import com.example.carmo.products.Product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("tobacco")
public class Tobacco extends Product {
    private String tobaccoType;
    private boolean hasFilter;
}
