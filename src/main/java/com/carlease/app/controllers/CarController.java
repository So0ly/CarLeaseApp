package com.carlease.app.controllers;

import com.carlease.app.models.Car;
import com.carlease.app.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService){
        this.carService = carService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Car>> getAllCars(){
        return carService.findAllCars().isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(carService.findAllCars());
    }

    @GetMapping("/{type}")
    public ResponseEntity<Car> getCarById(@PathVariable("type") String carType){
        Car selectedCar = carService.findCarByCarType(carType).orElse(null);
        return selectedCar == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(selectedCar);
    }
}
