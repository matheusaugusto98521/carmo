package com.example.carmo.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.carmo.clients.ClientModel;
import com.example.carmo.repository.IClientRepository;

@Service
public class AuthorizedServices implements UserDetailsService{

    @Autowired
    IClientRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientModel client = (ClientModel) this.repository.findByUsername(username);

        if (client == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        // Converte o ClientModel para UserDetails (ou use sua própria implementação)
        return new User(client.getUsername(), client.getPassword(), Collections.emptyList());
    }
    
}
