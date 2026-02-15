package com.carlease.app.services;

import com.carlease.app.models.Car;
import com.carlease.app.models.CarTypes;
import com.carlease.app.repos.CarRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepo carRepo;

    public CarServiceImpl(CarRepo carRepo){
        this.carRepo = carRepo;
    }

    public List<Car> findAllCars() {
        return carRepo.findAll();
    }

    public Optional<Car> findCarByCarType(String carTypeStr) throws IllegalArgumentException {
        CarTypes carType;
        try {
            carType = CarTypes.valueOf(carTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid car type");
        }
        return carRepo.findCarByCarType(carType);
    }
}
