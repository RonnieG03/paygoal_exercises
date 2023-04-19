package com.exercises.paygoal.controller;


import com.exercises.paygoal.model.Product;
import com.exercises.paygoal.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product){
        LOGGER.info("Create new product{}",product);
        return ResponseEntity.ok(productService.save(product));
    }
    @GetMapping()
    public ResponseEntity<List<Product>> list(){
        LOGGER.info("Get product list");
        return ResponseEntity.ok(productService.findAll());
    }
    @GetMapping("/priceAsc")
    public ResponseEntity<List<Product>> listProductPriceAsc(){
        LOGGER.info("Get product list Order price Asc");
        return ResponseEntity.ok(productService.getAllProductOrderByPriceAsc());
    }
    @GetMapping("/priceDesc")
    public ResponseEntity<List<Product>> listProductPriceDesc() {
        LOGGER.info("Get product list Order price Desc");
        return ResponseEntity.ok(productService.getAllProductOrderByPriceDesc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        LOGGER.info("get product by id{}", id);
        Optional<Product> productOptional = productService.getProductById(id);
        return productOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Long id) {
        LOGGER.info("Update product{}",product);
        Optional<Product> productOptional = productService.getProductById(id);
        return productOptional
                .map(theProduct -> {
                    theProduct.setName(product.getName());
                    theProduct.setDescription(product.getDescription());
                    theProduct.setPrice(product.getPrice());
                    theProduct.setQuantity(product.getQuantity());
                    return ResponseEntity.ok(productService.update(theProduct));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id){
        LOGGER.info("Delete product{}",id);
        Optional<Product> productOptional = productService.getProductById(id);
        return productOptional
                .map(product -> {
                    productService.delete(product.getId());
                    return ResponseEntity.ok(product);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
    @PostMapping("save/list")
    public ResponseEntity<List<Product>> saveList(@RequestBody List<Product> products){
        LOGGER.info("Create new product List{}",products);
        return ResponseEntity.ok(productService.saveList(products));

    }
}
