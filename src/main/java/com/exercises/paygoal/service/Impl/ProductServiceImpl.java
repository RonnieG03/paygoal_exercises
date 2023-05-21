package com.exercises.paygoal.service.Impl;

import com.exercises.paygoal.model.Product;
import com.exercises.paygoal.repository.ProductRepository;
import com.exercises.paygoal.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Product save(Product product){
        Optional<Product> optionalProduct = Optional.ofNullable(productRepository.findProductByName(product.getName()));
        optionalProduct.ifPresent(existingProduct -> {
            LOGGER.error("Product already exists:{}", existingProduct);
            throw new IllegalArgumentException("Product already exists: " + existingProduct.getName());
        });
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
    public Optional<Product> update(UUID id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        return existingProduct.map(updateProduct -> {
                    updateProduct.setName(product.getName());
                    updateProduct.setDescription(product.getDescription());
                    updateProduct.setPrice(product.getPrice());
                    updateProduct.setQuantity(product.getQuantity());
                    return productRepository.save(updateProduct);
                });
    }

    @Override
    public void delete(UUID id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        existingProduct.map(delteteProduct -> {
                    productRepository.deleteById(delteteProduct.getId());
                    return null;
                });
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
        Set<String> existingProductNames = productRepository.findAll().stream()
                .map(Product::getName)
                .collect(Collectors.toSet());

        return products.stream()
                .filter(product -> !existingProductNames.contains(product.getName()))
                .map(productRepository::save)
                .collect(Collectors.toList());
    }
    
}