package com.example.carmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmo.products.ProductModel;

public interface IProductRepository extends JpaRepository<ProductModel, Long>{
    
}
