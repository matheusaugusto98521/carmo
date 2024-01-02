package com.example.carmo.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.carmo.clients.ClientModel;
import com.example.carmo.products.CarItem;
import com.example.carmo.products.Order;
import com.example.carmo.products.OrderItem;
import com.example.carmo.repository.IOrderRepository;

@Service
public class OrderService {

    @Autowired
    IOrderRepository repository;
    
    public Order creatOrder(ClientModel client, List<CarItem> carItems){
        Order order = new Order();
        order.setClient(client);

        for(CarItem carItem : carItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(carItem.getProduct());
            orderItem.setQuantity(carItem.getQuantity());
            BigDecimal price = BigDecimal.valueOf(carItem.getProduct().getPrice());
            orderItem.setPrice(price);

            order.getItems().add(orderItem);
            orderItem.setOrder(order);
        }

        return repository.save(order);
    }

    public BigDecimal calculateOrderTotal(Order order){
        return order.getItems().stream()
        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
