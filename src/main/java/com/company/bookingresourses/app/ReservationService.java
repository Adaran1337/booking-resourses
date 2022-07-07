package com.company.bookingresourses.app;

import com.company.bookingresourses.entity.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReservationService {
    public boolean compareOffices(Reservation reservation) {
        UUID employeeOffice = reservation.getEmployee().getOffice().getId();
        UUID resourceOffice = reservation.getResources().getOffice().getId();
        return employeeOffice.equals(resourceOffice);
    }

    public boolean timeIntersection(Reservation reservation) {
        LocalDateTime inputStartDate = reservation.getStartDate();
        LocalDateTime inputEndDate = reservation.getEndDate();

        for (Reservation resourceReservation : reservation.getResources().getReservations()) {
            LocalDateTime resourceStartDate = resourceReservation.getStartDate();
            LocalDateTime resourceEndDate = resourceReservation.getEndDate();
            if (!((resourceEndDate.isBefore(inputStartDate) && resourceEndDate.isBefore(inputEndDate)) ||
                    (resourceStartDate.isAfter(inputStartDate) && resourceStartDate.isAfter(inputEndDate)))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTime(Reservation reservation) {
        LocalDateTime inputStartDate = reservation.getStartDate();
        LocalDateTime inputEndDate = reservation.getEndDate();

        return !inputStartDate.isAfter(inputEndDate) && !inputStartDate.isBefore(LocalDateTime.now());
    }
}