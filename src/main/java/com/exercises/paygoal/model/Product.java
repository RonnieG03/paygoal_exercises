package com.exercises.paygoal.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private Double price;
    @NotNull
    private int quantity;
}
