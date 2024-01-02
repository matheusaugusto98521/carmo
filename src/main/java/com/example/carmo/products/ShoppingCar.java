package com.example.carmo.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.carmo.clients.ClientModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_shopping_car")
public class ShoppingCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shoppingCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarItem> carItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private ClientModel client;


    public void addItem(Product product, int quantity){
        
        CarItem existingItem = findByCarItemProduct(product);

        if(existingItem != null){
            existingItem.setQuantity(existingItem.getQuantity()+quantity);
        }else{
            CarItem car = new CarItem();
            car.setProduct(product);
            car.setQuantity(quantity);
            car.setShoppingCar(this);
            carItems.add(car);
        }
        
    }


    public CarItem findByCarItemProduct(Product product){
        
        for(CarItem car : carItems){
            if(car.getProduct().equals(product)){
                return car;
            }
        }
        
        return null;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, carItems, client);
    }
}
