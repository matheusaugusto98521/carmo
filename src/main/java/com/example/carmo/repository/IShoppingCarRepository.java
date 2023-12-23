package com.example.carmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmo.products.ShoppingCar;

public interface IShoppingCarRepository extends JpaRepository<ShoppingCar, Long> {
    
}
