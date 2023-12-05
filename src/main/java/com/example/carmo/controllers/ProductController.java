package com.example.carmo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmo.products.ProductModel;
import com.example.carmo.products.ProductResponseDTO;
import com.example.carmo.repository.IProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductRepository prodRepository;

    @PostMapping("/")
    public ResponseEntity<?> addProduct(@RequestBody ProductModel product) {

        if (product != null) {
            if (product.getId() != null && prodRepository.existsById(product.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse produto já existe");
            }
        }
        ProductModel addedProd = prodRepository.save(product);

        if (addedProd != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ProductResponseDTO("Produto criado com sucesso!", addedProd));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductResponseDTO("Erro ao criar produto", null));
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<ProductModel>> displayProducts() {
        List<ProductModel> display = prodRepository.findAll();

        if (display.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(display);

    }

    @PutMapping("/{id}/")
    public ResponseEntity<?> alterateProduct(@PathVariable Long id, @RequestBody ProductModel upProduct) {

        Optional<ProductModel> existingProductOptional = prodRepository.findById(id);

        if (existingProductOptional.isPresent()) {
            ProductModel existingProduct = existingProductOptional.get();

            existingProduct.setName(upProduct.getName());
            existingProduct.setDescription(upProduct.getDescription());
            existingProduct.setPrice(upProduct.getPrice());

            ProductModel updatedproduct = prodRepository.save(existingProduct);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ProductResponseDTO("Produto alterado com sucesso!", updatedproduct));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProductResponseDTO("Produto não encontrado", null));
        }

    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try{
            if (prodRepository.existsById(id)) {
                prodRepository.deleteById(id);

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ProductResponseDTO("Produto excluído com sucesso!", null));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductResponseDTO("Produt não encontrado!", null));
            }
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductResponseDTO("Erro ao excluir produto!", null));
        }


    }
}
