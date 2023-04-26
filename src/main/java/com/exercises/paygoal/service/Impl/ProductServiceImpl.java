package com.exercises.paygoal.service.Impl;

import com.exercises.paygoal.model.Product;
import com.exercises.paygoal.repository.ProductRepository;
import com.exercises.paygoal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    
    @Override
    public Product save(Product product){
        Product existingProduct = productRepository.findProductByName(product.getName());
        if (existingProduct != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "product name already exists");
        }
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return Optional.ofNullable(productRepository.findProductByName(name));
    }

    @Override
    public Product update(UUID id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
        Product updateProduct = existingProduct.get();
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setQuantity(product.getQuantity());
        return productRepository.save(updateProduct);
        }
        return product;
    }

    @Override
    public void delete(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.get();
        if(optionalProduct.isPresent()){
            productRepository.deleteById(product.getId());
        }
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductOrderByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public List<Product> getAllProductOrderByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public List<Product> saveList(List<Product> products) {
        return productRepository.saveAll(products);
    }
    
}
