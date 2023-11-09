package org.exercise.java.springlamiapizzeriacrud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
}
