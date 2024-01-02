package com.example.carmo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.carmo.clients.ClientModel;
import com.example.carmo.dto.CarItemDTO;
import com.example.carmo.dto.CarItemResponseDTO;
import com.example.carmo.dto.ProductDTO;
import com.example.carmo.dto.ProductResponseDTO;
import com.example.carmo.product_models.AlcholicBeverage;
import com.example.carmo.product_models.Food;
import com.example.carmo.product_models.NonAlcholicBeverage;
import com.example.carmo.product_models.Tobacco;
import com.example.carmo.product_models.Various;
import com.example.carmo.products.CarItem;
import com.example.carmo.products.Product;
import com.example.carmo.products.ShoppingCar;
import com.example.carmo.repository.ICarItemRepository;
import com.example.carmo.repository.IClientRepository;
import com.example.carmo.repository.IProductRepository;
import com.example.carmo.repository.IShoppingCarRepository;
import com.example.carmo.services.ProductServices;
import com.example.carmo.services.ShoppingCarService;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private IProductRepository prodRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private ProductServices services;

    @Autowired
    private ShoppingCarService carService;

    @Autowired
    private IShoppingCarRepository carRepository;

    @Autowired
    ICarItemRepository carItemRepository;

    @PostMapping("/{productType}")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO dto, @PathVariable String productType) {

        if (dto != null) {
            if (dto != null && dto.name() != null && prodRepository.existsByName(dto.name())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse produto já existe");
            }
        }

        switch (productType.toLowerCase()) {
            case "alcoholic":
                AlcholicBeverage alcholicBeverage = new AlcholicBeverage();
                alcholicBeverage.setName(dto.name());
                alcholicBeverage.setDescription(dto.description());
                alcholicBeverage.setPrice(dto.price());
                alcholicBeverage.setAlcPercent(dto.alcPercent());
                alcholicBeverage.setOrigin(dto.origin());

                var savedBev = prodRepository.save(alcholicBeverage);

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ProductResponseDTO("Produto criado com sucesso!", savedBev));

            case "non_alcoholic":
                NonAlcholicBeverage nonAlcholicBeverage = new NonAlcholicBeverage();
                nonAlcholicBeverage.setName(dto.name());
                nonAlcholicBeverage.setDescription(dto.description());
                nonAlcholicBeverage.setPrice(dto.price());
                NonAlcholicBeverage savedNon = prodRepository.save(nonAlcholicBeverage);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ProductResponseDTO("Produto criado com sucesso!", savedNon));

            case "tobacco":
                Tobacco tobacco = new Tobacco();
                tobacco.setName(dto.name());
                tobacco.setDescription(dto.description());
                tobacco.setPrice(dto.price());
                tobacco.setTobaccoType(dto.tobaccoType());
                tobacco.setHasFilter(dto.hasFilter());
                Tobacco savedTo = prodRepository.save(tobacco);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ProductResponseDTO("Produto criado com sucesso!", savedTo));

            case "food":
                Food food = new Food();
                food.setName(dto.name());
                food.setDescription(dto.description());
                food.setPrice(dto.price());
                var savedFood = prodRepository.save(food);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ProductResponseDTO("Produto criado com sucesso!", savedFood));

            case "various":
                Various various = new Various();
                various.setName(dto.name());
                various.setDescription(dto.description());
                various.setPrice(dto.price());
                var savedVarious = prodRepository.save(various);
                System.out.println("Tipo do objeto recebido: " + dto.getClass().getSimpleName());

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ProductResponseDTO("Produto criado com sucesso!", savedVarious));

            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo não encontrado");
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> displayProducts() {
        List<Product> display = prodRepository.findAll();

        if (display.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(display);

    }


    @PutMapping("/{id}/")
    public ResponseEntity<?> alterateProduct(@PathVariable Long id, @RequestBody Product upProduct) {

        Optional<Product> existingProductOptional = prodRepository.findById(id);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();

            existingProduct.setName(upProduct.getName());
            existingProduct.setDescription(upProduct.getDescription());
            existingProduct.setPrice(upProduct.getPrice());

            Product updatedproduct = prodRepository.save(existingProduct);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ProductResponseDTO("Produto alterado com sucesso!", updatedproduct));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProductResponseDTO("Produto não encontrado", null));
        }

    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            if (prodRepository.existsById(id)) {
                prodRepository.deleteById(id);

                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ProductResponseDTO("Produto excluído com sucesso!", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ProductResponseDTO("Produt não encontrado!", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductResponseDTO("Erro ao excluir produto!", null));
        }
    }

    @PostMapping("/{productId}/assign-category/{categoryId}")
    public ResponseEntity<?> assingCategory(@PathVariable Long productId, @PathVariable Long categoryId) {

        Product product = services.assignCategory(productId, categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProductResponseDTO("Produto atribuído à categoria com sucesso", product));
    }

    @PostMapping("/{productId}/add-to-car/{clientId}")
    public ResponseEntity<?> addToCar(@PathVariable Long productId,
            @PathVariable String clientId, @RequestParam int quantity) {

                Optional<Product> productOptional = prodRepository.findById(productId);
                Optional<ClientModel> clientOptional = clientRepository.findById(clientId);

                if(productOptional.isPresent() && clientOptional.isPresent()){
                    Product product = productOptional.get();
                    ClientModel client = clientOptional.get();

                    carService.addToCar(product, quantity, client);

                    ShoppingCar shopCar = carService.getShoppingCar(client);

                    List<CarItem> carItems = shopCar.getCarItems();

                    carItemRepository.saveAll(carItems);

                    List<CarItemDTO> carItemDTO = carService.convertToDTO(carItems);
                    
                    double total = carService.total(shopCar);

                    CarItemResponseDTO responseDTO = new CarItemResponseDTO("Produto adicionado ao carrinho", carItemDTO, total);

                    responseDTO.setTotal(total);
                    

                    return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
                }
                else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto ou cliente não encontrado");
                }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam("q") String query, Model model){

        List<Product> products = services.searchProduct(query);
        model.addAttribute("products", products);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/details/{clientId}")
    public ResponseEntity<List<CarItemDTO>> getShoppingCartDetails(@PathVariable String clientId) {
        ClientModel client = clientRepository.findById(clientId).orElse(null);

        if (client != null) {
            ShoppingCar shoppingCar = carService.getShoppingCar(client);

            if (shoppingCar != null) {
                List<CarItem> carItems = shoppingCar.getCarItems();
                List<CarItemDTO> carItemDTOList = carService.convertToDTO(carItems);
                return ResponseEntity.ok(carItemDTOList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}