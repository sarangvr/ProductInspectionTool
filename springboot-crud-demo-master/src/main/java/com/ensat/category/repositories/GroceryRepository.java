package com.ensat.category.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensat.category.entities.Grocery;

public interface GroceryRepository extends JpaRepository<Grocery, Long>{

}
