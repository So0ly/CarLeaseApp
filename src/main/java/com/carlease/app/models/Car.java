package com.carlease.app.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cars_seq")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "car_type")
    private CarTypes carType;
    private int capacity;
    private int pricePerDay;

    public Car(CarTypes carType, int capacity, int pricePerDay) {
        this.carType = carType;
        this.capacity = capacity;
        this.pricePerDay = pricePerDay;
    }
}
