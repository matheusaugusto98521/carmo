package com.example.carmo.clients;

import lombok.Data;

@Data
public class ClientResponseDTO {
    
    private String message;
    private ClientModel client;

    public ClientResponseDTO(String message, ClientModel client){
        this.message = message;
        this.client = client;
    }
}
