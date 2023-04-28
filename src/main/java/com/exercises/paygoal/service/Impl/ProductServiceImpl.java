package com.exercises.paygoal.service.Impl;

import com.exercises.paygoal.model.Product;
import com.exercises.paygoal.repository.ProductRepository;
import com.exercises.paygoal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    
    @Override
    public Product save(Product product){
        Product existingProduct = productRepository.findProductByName(product.getName());
        if (existingProduct == null)
            productRepository.save(product);
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "product name already exists");   
        return product;
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
        productRepository.save(updateProduct);
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
        Set<String> productNames = new HashSet<>();
        List<Product> savedProducts = products.stream()
            .map(product -> {
                Optional<Product> existingProduct = Optional.ofNullable(productRepository.findProductByName(product.getName()));
                if (existingProduct.isPresent()) {
                    return existingProduct.get();
                } else {
                    productNames.add(product.getName());
                    return productRepository.save(product);
                }
            })
            .collect(Collectors.toList());
        return savedProducts;
    }
    
}
