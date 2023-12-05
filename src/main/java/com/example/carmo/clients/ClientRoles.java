package com.example.carmo.clients;


public enum ClientRoles {
    ADMIN("admin"),

    CLIENT("client");

    private String role;

    ClientRoles(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
