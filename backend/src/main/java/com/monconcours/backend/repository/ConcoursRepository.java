package com.monconcours.backend.repository;

import com.monconcours.backend.entity.Concours;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConcoursRepository extends JpaRepository<Concours, Integer> {
    List<Concours> findByPublicConcoursTrue();

    long countByPublicConcoursTrue();
}
