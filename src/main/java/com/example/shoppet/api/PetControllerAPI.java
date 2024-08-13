package com.example.shoppet.api;

import com.example.shoppet.model.Pet;
import com.example.shoppet.repository.ICategoryPet;
import com.example.shoppet.repository.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(value = "*")
public class PetControllerAPI {

    @Autowired
    private IPetRepository petRepository;

    @Autowired
    private ICategoryPet categoryRepository;

    @GetMapping("")
    public ResponseEntity<Page<Pet>> getAllPets(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "breed", required = false) String breed,
            @RequestParam(name = "popular", defaultValue = "false") boolean popular,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Pet> petPage;

        // Điều kiện 1: Lọc theo category và breed
        if (category != null && !category.isEmpty() && breed != null && !breed.isEmpty()) {
            petPage = petRepository.findByCategory_NameIgnoreCaseAndPetDetail_BreedIgnoreCase(category, breed, pageable);

            // Điều kiện 2: Lọc theo category và popular
        } else if (category != null && !category.isEmpty() && popular) {
            petPage = petRepository.findByCategory_NameIgnoreCaseAndPetDetail_Popular(category, true, pageable);

            // Điều kiện 3: Chỉ lọc theo category
        } else if (category != null && !category.isEmpty()) {
            petPage = petRepository.findByCategory_NameIgnoreCase(category, pageable);

            // Điều kiện 4: Lọc theo breed
        } else if (breed != null && !breed.isEmpty()) {
            petPage = petRepository.findByPetDetail_BreedIgnoreCase(breed, pageable);

            // Điều kiện 5: Lọc theo popular
        } else if (popular) {
            petPage = petRepository.findByPetDetail_Popular(true, pageable);

            // Điều kiện 6: Không có filter nào, trả về tất cả pets
        } else {
            petPage = petRepository.findAll(pageable);
        }

        return ResponseEntity.ok(petPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable long id) {
        Pet pet = petRepository.findById(id).orElse(null);

        if (pet != null) {
            return ResponseEntity.ok(pet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
