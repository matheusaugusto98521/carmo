package com.example.carmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmo.products.CarItem;

public interface ICarItemRepository extends JpaRepository<CarItem, Long>{
    
}
