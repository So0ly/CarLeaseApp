package com.carlease.app.repos;

import com.carlease.app.models.Car;
import com.carlease.app.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> getReservationsByCarIdAndEndDateLessThan(Long carId, Date startDate);
    @Query("""
    SELECT car
    FROM Car car
    LEFT JOIN Reservation res
        ON res.car = car
       AND res.startDate < :end
       AND res.endDate > :start
    GROUP BY car
    HAVING COUNT(res) < car.capacity
""")
    List<Car> findAvailableCarsInPeriod(
            LocalDate start,
            LocalDate end
    );

}
