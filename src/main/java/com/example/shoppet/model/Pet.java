package com.example.shoppet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int quantity;
    private double price;
    private String image;
    private String description;



    @OneToOne
    private PetDetail petDetail;

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;

}
