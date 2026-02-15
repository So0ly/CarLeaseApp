package com.carlease.app.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "reservations_seq")
    private Long id;
    @ManyToOne
    private Car car;
    @ManyToOne
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;
    private long finalPrice;

    public Reservation(Car car, User user, LocalDate startDate, LocalDate endDate) {
        this.car = car;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finalPrice = car.getPricePerDay() * (ChronoUnit.DAYS.between(startDate, endDate));
    }
}
