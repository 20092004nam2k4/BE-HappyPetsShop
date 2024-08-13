package com.example.shoppet.repository;

import com.example.shoppet.model.PetDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPetDetailRepository extends JpaRepository<PetDetail, Long> {
}
