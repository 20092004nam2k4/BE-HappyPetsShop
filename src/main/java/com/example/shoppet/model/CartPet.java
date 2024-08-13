package com.example.shoppet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
    private int quantity; // Số lượng sản phẩm trong giỏ hàng

    @Transient
    private double totalPrice; // Tổng giá cho số lượng sản phẩm này

    @PostLoad
    public void calculateTotalPrice() {
        if (pet != null) {
            this.totalPrice = pet.getPrice() * quantity;
        }
    }
}
