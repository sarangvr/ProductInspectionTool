package com.ensat.category.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensat.category.entities.Dairy;

public interface DairyRepository extends JpaRepository<Dairy, Long>{

}
