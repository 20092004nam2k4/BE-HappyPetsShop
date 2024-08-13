package com.example.shoppet.service;

import com.example.shoppet.model.CartPet;
import com.example.shoppet.model.Pet;
import com.example.shoppet.repository.ICartPetRepository;
import com.example.shoppet.repository.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartPetService {

    @Autowired
    private ICartPetRepository cartPetRepository;

    @Autowired
    private IPetRepository petRepository;

    public CartPet addPetToCart(long petId, int quantity) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));

        CartPet cartPet = new CartPet();
        cartPet.setPet(pet);
        cartPet.setQuantity(quantity);
        cartPet.calculateTotalPrice(); // Tính toán tổng giá cho số lượng

        return cartPetRepository.save(cartPet);
    }
}
