package com.carlease.app.services;

import com.carlease.app.exceptions.CarNotAvailableException;
import com.carlease.app.exceptions.CarTypeNotFoundException;
import com.carlease.app.exceptions.DateInputParseException;
import com.carlease.app.exceptions.IncorrectTimePeriodException;
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

    public Reservation createReservation(String carType, String startDateStr, String endDateStr) {
        User currentUser = userService.findCurrentUser();
        Car selectedCar = carService.findCarByCarType(carType).orElseThrow(
                () -> new CarTypeNotFoundException("Car type not found: " + carType));
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(startDateStr);
            endDate = LocalDate.parse(endDateStr);
        } catch (DateTimeParseException e) {
            throw new DateInputParseException("Invalid date format. Expected format: yyyy-MM-dd, received: "
                    + startDateStr + " " + endDateStr);
        }
        if (startDate.isAfter(endDate)) {
            throw new IncorrectTimePeriodException("Start date must be before end date");
        }
        LOGGER.debug("Creating reservation for car: {}, from {} to {}", selectedCar, startDate, endDate);
        if (!reservationRepo.findAvailableCarsInPeriod(startDate, endDate).contains(selectedCar)) {
            throw new CarNotAvailableException("No cars of " + selectedCar.getCarType() + " type available");
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
