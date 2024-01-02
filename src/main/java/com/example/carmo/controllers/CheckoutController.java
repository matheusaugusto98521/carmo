package com.example.carmo.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmo.clients.ClientModel;
import com.example.carmo.products.CarItem;
import com.example.carmo.products.Order;
import com.example.carmo.products.ShoppingCar;
import com.example.carmo.repository.IClientRepository;
import com.example.carmo.services.OrderService;
import com.example.carmo.services.ShoppingCarService;

@RestController
@RequestMapping("/checkout")
@CrossOrigin("*")
public class CheckoutController {
    
    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShoppingCarService shoppingCarService;

    @PostMapping("/finalize/{clientId}")
    public ResponseEntity<?> finalizeOrder(@PathVariable String clientId) {
        ClientModel client = clientRepository.findById(clientId).orElse(null);
        ShoppingCar shoppingCar = shoppingCarService.getShoppingCar(client);
    
        if (client != null && shoppingCar != null) {
            List<CarItem> carItems = shoppingCar.getCarItems();
    
            if (!carItems.isEmpty()) {
                Order order = orderService.creatOrder(client, carItems);
                BigDecimal total = orderService.calculateOrderTotal(order);
    
                boolean paymentAccepted = simulatePayment();
    
                if (paymentAccepted) {
                    shoppingCarService.clearItems(client);
                    return ResponseEntity.ok("Pedido finalizado R$ " + total);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pagamento não foi aceito.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O carrinho está vazio.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado.");
        }
    }

    public boolean simulatePayment(){
        Random random = new Random();

        double chanceOfAccepted = 0.85;

        return random.nextDouble() < chanceOfAccepted;
    }
}
