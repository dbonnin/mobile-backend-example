package com.dbonnin.mobile_backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    
}
