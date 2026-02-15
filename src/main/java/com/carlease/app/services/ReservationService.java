package com.carlease.app.services;

import com.carlease.app.models.Car;
import com.carlease.app.models.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    Reservation createReservation(String carType, String startDate, String endDate);
    List<Car> findAllAvailableCarsInPeriod(LocalDate startDate, LocalDate endDate);
}
