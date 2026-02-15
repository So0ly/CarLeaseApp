package com.carlease.app.services;

import com.carlease.app.exceptions.CarTypeNotFoundException;
import com.carlease.app.exceptions.DateInputParseException;
import com.carlease.app.exceptions.IncorrectTimePeriodException;
import com.carlease.app.models.Car;
import com.carlease.app.models.CarTypes;
import com.carlease.app.models.Reservation;
import com.carlease.app.models.User;
import com.carlease.app.repos.ReservationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private CarService carService;

    @Mock
    private ReservationRepo reservationRepo;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private User user;
    private Car car;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");

        car = new Car();
        car.setCarType(CarTypes.SUV);
    }

    @Test
    @DisplayName("Given correct reservation parameters when creating a new reservation, " +
            "reservation should be placed correctly")
    void givenCorrectReservationParams_whenCreatingReservation_thenCreateSuccessfully() {
        when(userService.findCurrentUser()).thenReturn(user);
        when(carService.findCarByCarType("SUV")).thenReturn(Optional.of(car));
        when(reservationRepo.findAvailableCarsInPeriod(
                LocalDate.of(2026, 1, 10),
                LocalDate.of(2026, 1, 15)
        )).thenReturn(List.of(car));

        Reservation result = reservationService.createReservation(
                "SUV",
                "2026-01-10",
                "2026-01-15"
        );

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(car, result.getCar());
        assertEquals(LocalDate.of(2026, 1, 10), result.getStartDate());
        assertEquals(LocalDate.of(2026, 1, 15), result.getEndDate());

        verify(reservationRepo).saveAndFlush(any(Reservation.class));
    }

    @Test
    @DisplayName("Given wrong car type when creating reservation, reservation should not be placed")
    void givenWrongCarType_whenCreatingReservation_thenReturnNullReservation() {
        when(userService.findCurrentUser()).thenReturn(user);
        when(carService.findCarByCarType("Car of the future")).thenReturn(Optional.empty());

         assertThrows(CarTypeNotFoundException.class, () -> reservationService.createReservation(
                "Car of the future",
                "2026-01-10",
                "2026-01-15"
            )
         );
    }

    @ParameterizedTest
    @DisplayName("Given malformed date when creating reservation, reservation should not be placed")
    @ValueSource(strings = {"2026-01-32", "2026-13-01", "", "not-even-aDate"})
    void givenMalformedDate_whenCreatingReservation_thenThrowException(String startDate) {
        when(carService.findCarByCarType("SUV"))
                .thenReturn(Optional.of(car));

        assertThrows(
                DateInputParseException.class,
                () -> reservationService.createReservation(
                        "SUV", String.valueOf(startDate), "15-01-2026"
                )
        );
    }

    @Test
    @DisplayName("Given end date before start date when creating reservation, reservation should not be placed")
    void givenEndDateBeforeStartDate_whenCreatingReservation_thenThrowException() {
        when(carService.findCarByCarType("SUV"))
                .thenReturn(Optional.of(car));

        assertThrows(
                IncorrectTimePeriodException.class,
                () -> reservationService.createReservation(
                        "SUV", "2026-01-20", "2026-01-10"
                )
        );
    }
}
