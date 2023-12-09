package com.example.carmo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmo.products.ProductCategory;


public interface ICategoryRepository extends JpaRepository<ProductCategory, Long>{
    boolean existsByName(String name);
    Optional<ProductCategory> findById(Long id);
}
