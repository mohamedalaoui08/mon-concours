package com.monconcours.backend.repository;

import com.monconcours.backend.entity.ContenuService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContenuServiceRepository
        extends JpaRepository<ContenuService, Integer> {

    List<ContenuService> findAllByOrderByOrdreAsc();
    ContenuService findTopByOrderByOrdreDesc();

}