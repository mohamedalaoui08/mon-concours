package com.monconcours.backend.repository;

import com.monconcours.backend.entity.Exercice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciceRepository extends JpaRepository<Exercice, Integer> {

}