package com.example.carmo.dto;

import com.example.carmo.clients.ClientRoles;

public record RegisterDTO(String username, String password, ClientRoles role) {
    
}
