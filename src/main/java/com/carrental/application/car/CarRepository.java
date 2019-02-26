package com.carrental.application.car;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByBrand(String brand);
    List<Car> findByType(String type);
}
