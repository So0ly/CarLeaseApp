package com.carlease.app.controllers;

import com.carlease.app.models.ReservationResponse;
import com.carlease.app.models.Reservation;
import com.carlease.app.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ReservationResponse> createReservation(@RequestParam String carType,
                                                                 @RequestParam String startDate,
                                                                 @RequestParam String endDate) {
        Reservation reservation = reservationService.createReservation(carType, startDate, endDate);
        return reservation == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(ReservationResponse.from(reservation));
    }
}
