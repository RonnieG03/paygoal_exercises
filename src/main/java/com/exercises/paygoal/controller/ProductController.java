package com.exercises.paygoal.controller;


import com.exercises.paygoal.model.Product;
import com.exercises.paygoal.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product){
        LOGGER.info("Create new product{}",product);
        Optional<Product> saveProduct = productService.save(product);
        return saveProduct
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping()
    public ResponseEntity<List<Product>> list(){
        LOGGER.info("Get product list");
        return Optional.ofNullable(productService.findAll())
        .filter(list -> !list.isEmpty())
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/priceAsc")
    public ResponseEntity<List<Product>> listProductPriceAsc(){
        LOGGER.info("Get product list Order price Asc");
        return Optional.ofNullable(productService.getAllProductOrderByPriceAsc())
        .filter(list -> !list.isEmpty())
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/priceDesc")
    public ResponseEntity<List<Product>> listProductPriceDesc() {
        LOGGER.info("Get product list Order price Desc");
        return Optional.ofNullable(productService.getAllProductOrderByPriceDesc())
        .filter(list -> !list.isEmpty())
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        LOGGER.info("get product by id{}", id);
        Optional<Product> productOptional = productService.getProductById(id);
        return productOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable UUID id) {
        LOGGER.info("Update product{}",product);
        Optional<Product> optionalProduct = productService.update(id, product);
        return optionalProduct
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable UUID id){
        LOGGER.info("Delete product{}",id);
        Optional<Product> deleteProduct = productService.delete(id);
        return deleteProduct
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("save/list")
    public ResponseEntity<List<Product>> saveList(@RequestBody List<Product> products){
        LOGGER.info("Create new product List{}",products);
        return Optional.ofNullable(productService.saveList(products))
        .filter(list -> !list.isEmpty())
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
