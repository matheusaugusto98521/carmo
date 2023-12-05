package com.example.carmo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.carmo.clients.ClientModel;


public interface IClientRepository extends JpaRepository<ClientModel, String>{
    UserDetails findByUsername(String username);

}
