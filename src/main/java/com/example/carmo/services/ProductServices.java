package com.example.carmo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.carmo.products.Product;
import com.example.carmo.products.ProductCategory;
import com.example.carmo.repository.ICategoryRepository;
import com.example.carmo.repository.IProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServices {

    @Autowired
    IProductRepository repository;

    @Autowired
    ICategoryRepository categoryRepository;

    public Product assignCategory(Long productId, Long categoryId){

        if(productId == null || categoryId == null){
            throw new IllegalArgumentException("O ID de categoria ou do produto específico não podem ser nulos");
        }

        Product product = repository.findById(productId).orElseThrow(
            () -> new EntityNotFoundException("Produto não encontrado"));


        ProductCategory category = categoryRepository.findById(categoryId).orElseThrow(
            () -> new EntityNotFoundException("Categoria não encontrada"));

        if(product.getCategory() != null && product.getCategory().equals(category)){
            throw new IllegalStateException("O produto já está associado à categoria especificada");
        }


        product.setCategory(category);
        return repository.save(product);
    }

    public List<Product> getProductsByCategory(Long categoryID){
        Optional<ProductCategory> categoryOptional = categoryRepository.findById(categoryID);

        if(categoryOptional.isPresent()){
            ProductCategory category = categoryOptional.get();
            return category.getProducts();
        }else{
            throw new EntityNotFoundException("Categoria não encontrada com o ID:" + categoryID);
        }

        
    }
}
