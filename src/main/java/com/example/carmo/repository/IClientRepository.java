package com.example.carmo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carmo.clients.ClientModel;


public interface IClientRepository extends JpaRepository<ClientModel, String>{
    ClientModel findByUsername(String username);

}
