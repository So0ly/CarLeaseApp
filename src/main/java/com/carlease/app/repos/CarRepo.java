package com.carlease.app.repos;

import com.carlease.app.models.Car;
import com.carlease.app.models.CarTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    Optional<Car> findCarByCarType(CarTypes carType);
}
