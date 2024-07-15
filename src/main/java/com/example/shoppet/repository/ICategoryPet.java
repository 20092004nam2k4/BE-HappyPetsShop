package com.example.shoppet.repository;

import com.example.shoppet.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryPet extends JpaRepository<Category, Long> {
}
