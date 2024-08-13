package com.example.shoppet.api;

import com.example.shoppet.model.CartPet;
import com.example.shoppet.repository.ICartPetRepository;
import com.example.shoppet.service.CartPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(value = "*")
public class CartControllerAPI {

    @Autowired
    private ICartPetRepository repository;

    @Autowired
    private CartPetService cartPetService;

    @PostMapping("/add")
    public ResponseEntity<CartPet> addPetToCart(@RequestParam long petId, @RequestParam int quantity) {
        try {
            CartPet cartPet = cartPetService.addPetToCart(petId, quantity);
            return ResponseEntity.ok(cartPet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/items")
    public ResponseEntity<List<CartPet>> getCartItems() {
        List<CartPet> cartItems = repository.findAll(); // Giả sử bạn có phương thức này trong CartPetService
        return ResponseEntity.ok(cartItems);
    }

}
