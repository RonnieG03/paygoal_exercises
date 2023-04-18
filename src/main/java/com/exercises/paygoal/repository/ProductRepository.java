package com.exercises.paygoal.repository;

import com.exercises.paygoal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
