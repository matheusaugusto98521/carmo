package com.example.carmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmo.products.Order;

public interface IOrderRepository extends JpaRepository<Order, Long>{
    
}
