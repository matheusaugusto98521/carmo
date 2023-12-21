package com.example.carmo.services;

import org.springframework.stereotype.Service;

import com.example.carmo.clients.ClientModel;
import com.example.carmo.products.Product;
import com.example.carmo.products.ShoppingCar;

@Service
public class ShoppingCarService {
    

    public void addToCar(Product product, int quantity, ClientModel client){

        ShoppingCar shoppingCar = client.getShoppingCar();

        if(shoppingCar == null){
            ShoppingCar shopCar = new ShoppingCar();
            shopCar.setClient(client);
            client.setShoppingCar(shopCar);
            shoppingCar = shopCar;
        }
        
        shoppingCar.addItem(product, quantity);
}
}