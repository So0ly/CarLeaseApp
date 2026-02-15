package com.carlease.app.models;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class ReservationResponse {
    private String carType;
    private LocalDate startDate;
    private LocalDate endDate;
    private long finalPrice;

    public ReservationResponse(String carType, LocalDate startDate, LocalDate endDate, long finalPrice) {
        this.carType = carType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finalPrice = finalPrice;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getCar() != null && reservation.getCar().getCarType() != null
                        ? reservation.getCar().getCarType().toString() : null,
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getFinalPrice()
        );
    }
}
