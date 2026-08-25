package com.monconcours.backend.repository;

import com.monconcours.backend.entity.Concours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcoursRepository extends JpaRepository<Concours, Integer> {

}
