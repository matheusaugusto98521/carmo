package com.example.carmo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.carmo.clients.ClientModel;
import com.example.carmo.dto.CarItemDTO;
import com.example.carmo.products.CarItem;
import com.example.carmo.products.Product;
import com.example.carmo.products.ShoppingCar;

import jakarta.transaction.Transactional;

@Service
public class ShoppingCarService {

    @Transactional
    public void addToCar(Product product, int quantity, ClientModel client) {

        ShoppingCar shoppingCar = client.getShoppingCar();

        if (shoppingCar == null) {
            ShoppingCar shopCar = new ShoppingCar();
            shopCar.setClient(client);
            client.setShoppingCar(shopCar);
            shoppingCar = shopCar;
        }

        shoppingCar.addItem(product, quantity);
    }

    public ShoppingCar getShoppingCar(ClientModel client) {
        return client.getShoppingCar();
    }

    public List<CarItemDTO> convertToDTO(List<CarItem> carItems) {
        List<CarItemDTO> carItemDTOList = new ArrayList<>();

        for (CarItem carItem : carItems) {
            CarItemDTO carItemDTO = new CarItemDTO();
            carItemDTO.setId(carItem.getId());
            carItemDTO.setProduct(carItem.getProduct());;
            carItemDTO.setQuantity(carItem.getQuantity());

            // Adicione mais atributos se necessário

            carItemDTOList.add(carItemDTO);
        }

        return carItemDTOList;
    }

    public double total(ShoppingCar shoppingCar){
        List<CarItem> carItems = shoppingCar.getCarItems();
        double total = 0.0;

        for(CarItem carItem : carItems){
            Product product = carItem.getProduct();
            double itemTotal = product.getPrice() * carItem.getQuantity();
            total += itemTotal;
        }

        return total;
    }
}