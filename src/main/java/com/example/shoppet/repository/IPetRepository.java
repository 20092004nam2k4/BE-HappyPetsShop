package com.example.shoppet.repository;

import com.example.shoppet.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IPetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findByCategory_NameIgnoreCase(String category, Pageable pageable);
    Page<Pet> findByCategory_NameIgnoreCaseAndPetDetail_Popular(String category, boolean popular, Pageable pageable);

    Page<Pet> findByPetDetail_Popular(boolean b, Pageable pageable);

    Page<Pet> findByCategory_NameIgnoreCaseAndPetDetail_BreedIgnoreCase(String category, String breed, Pageable pageable);

    Page<Pet> findByPetDetail_BreedIgnoreCase(String breed, Pageable pageable);
}
