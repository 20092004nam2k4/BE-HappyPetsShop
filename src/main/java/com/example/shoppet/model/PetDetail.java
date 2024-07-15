package com.example.shoppet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PetDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String breed; // Giống loài
    private int age; // Tuổi
    private double weight; // Trọng lượng
    private String color; // Màu sắc
    private String healthStatus; // Tình trạng sức khỏe

    // Có thể thêm các thuộc tính khác nếu cần
}
