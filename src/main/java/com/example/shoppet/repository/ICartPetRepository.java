package com.example.shoppet.repository;

import com.example.shoppet.model.CartPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartPetRepository extends JpaRepository<CartPet, Long> {
}
