package com.carlease.app.services;

import com.carlease.app.models.Car;
import com.carlease.app.models.Reservation;
import com.carlease.app.models.User;
import com.carlease.app.repos.ReservationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);
    private final ReservationRepo reservationRepo;
    private final CarService carService;
    private final UserService userService;

    public ReservationServiceImpl(ReservationRepo reservationRepo,
                                  CarService carService, UserService userService){
        this.reservationRepo = reservationRepo;
        this.carService = carService;
        this.userService = userService;
    }

    public Reservation createReservation(String carType, String startDateStr, String endDateStr) throws DateTimeParseException {
        User currentUser = userService.findCurrentUser();
        Car selectedCar = carService.findCarByCarType(carType).orElse(null);
        if (selectedCar == null) {
            LOGGER.error("{} car type not found", carType);
            return null;
        }

        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(startDateStr);
            endDate = LocalDate.parse(endDateStr);
        } catch (DateTimeParseException e) {
            LOGGER.error("Invalid date format");
            return null;
        }
        if (startDate.isAfter(endDate)) {
            LOGGER.error("Start date must be before end date");
            return null;
        }
        LOGGER.debug("Creating reservation for car: {}, from {} to {}", selectedCar, startDate, endDate);
        if (!reservationRepo.findAvailableCarsInPeriod(startDate, endDate).contains(selectedCar)) {
            LOGGER.error("No cars of {} type available", selectedCar.getCarType());
            return null;
        }
        Reservation reservation = new Reservation(selectedCar, currentUser, startDate, endDate);
        reservationRepo.saveAndFlush(reservation);
        LOGGER.info(
                "Reservation created: id={}, user={}, carType={}, startDate={}, endDate={}, finalPrice={}",
                reservation.getId(),
                reservation.getUser() != null ? reservation.getUser().getUsername() : null,
                reservation.getCar() != null ? reservation.getCar().getCarType() : null,
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getFinalPrice()
        );
        return reservation;
    }

    public List<Car> findAllAvailableCarsInPeriod(LocalDate startDate, LocalDate endDate){
        return reservationRepo.findAvailableCarsInPeriod(startDate, endDate);
    }
}
