package com.exercises.paygoal.model;


import lombok.Data;

import java.util.UUID;

import javax.persistence.*;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private int quantity;
}
