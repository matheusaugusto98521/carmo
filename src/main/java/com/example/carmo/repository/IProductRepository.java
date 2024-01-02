package com.example.carmo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmo.products.Product;

public interface IProductRepository extends JpaRepository<Product, Long>{
    boolean existsByName(String name);
    List<Product> findByNameContaining(String query);
}
