package com.example.carmo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmo.products.ProductCategory;
import com.example.carmo.products.ProductModel;
import com.example.carmo.repository.ICategoryRepository;
import com.example.carmo.services.ProductServices;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryRepository repository;

    @Autowired
    ProductServices services;

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody ProductCategory category){
        if(category == null || category.getName().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria não pode ser nula ou vazia");
        }else if(repository.existsByName(category.getName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria ja existente");
        }
        else{
            var createdCategory = repository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        }
    }

    @GetMapping("/{categoryID}/products")
    public ResponseEntity<List<ProductModel>> getProductsByCategory(@PathVariable Long categoryID){
        try{
            List<ProductModel> products = services.getProductsByCategory(categoryID);

            return ResponseEntity.status(HttpStatus.OK).body(products);
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
}
