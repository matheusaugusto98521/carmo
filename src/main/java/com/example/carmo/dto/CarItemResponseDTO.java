package com.example.carmo.dto;

import java.util.List;

import lombok.Data;

@Data
public class CarItemResponseDTO {

    private String message;
    private List<CarItemDTO> carItems;
    private double total;
    
    public CarItemResponseDTO(String message, List<CarItemDTO> carItems, double total) {
        this.message = message;
        this.carItems = carItems;
        this.total = total;
    }
}
