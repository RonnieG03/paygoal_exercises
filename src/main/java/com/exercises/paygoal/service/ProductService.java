package com.exercises.paygoal.service;

import com.exercises.paygoal.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);
    Optional<Product> getProductById(Long id);
    Optional<Product> getProductByName(String name);
    Product update (Product product);
    void delete(Long id);
    List<Product> findAll();
    List<Product> saveList(List<Product> products);
}
