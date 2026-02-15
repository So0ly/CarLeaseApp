package com.carlease.app.services;

import com.carlease.app.models.Car;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> findAllCars();
    Optional<Car> findCarByCarType(String carType);
}
