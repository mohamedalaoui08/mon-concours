package com.monconcours.backend.repository;

import com.monconcours.backend.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationRepository extends JpaRepository<Formation, Integer> {

}
