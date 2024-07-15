package com.example.shoppet.repository;

import com.example.shoppet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPetRepository extends JpaRepository<Pet, Long> {
}
