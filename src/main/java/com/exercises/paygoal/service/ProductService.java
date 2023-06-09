package com.exercises.paygoal.service;

import com.exercises.paygoal.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Optional<Product> save(Product product);
    Optional<Product> getProductById(UUID id);
    Optional<Product> getProductByName(String name);
    Optional<Product> update (UUID id, Product product);
    Optional<Product> delete(UUID id);
    List<Product> findAll();
    List<Product> getAllProductOrderByPriceAsc();
    List<Product> getAllProductOrderByPriceDesc();
    List<Product> saveList(List<Product> products);
}
