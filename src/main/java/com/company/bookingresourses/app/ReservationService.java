package com.company.bookingresourses.app;

import com.company.bookingresourses.entity.Reservation;
import com.company.bookingresourses.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {
    @Autowired
    private CurrentUserService currentUserService;

    public boolean compareOffices(Reservation reservation) {
        UUID employeeOffice = reservation.getEmployee().getOffice().getId();
        UUID resourceOffice = reservation.getResource().getOffice().getId();
        return employeeOffice.equals(resourceOffice);
    }

    public boolean timeIntersection(Reservation reservation) {
        LocalDateTime inputStartDate = reservation.getStartDate();
        LocalDateTime inputEndDate = reservation.getEndDate();

        List<Reservation> resourceReservations = reservation.getResource().getReservations();
        resourceReservations.remove(reservation);

        for (Reservation resourceReservation : resourceReservations) {
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

    public User getCurrentEmployee() {
        User user = currentUserService.getCurrentUser();
        return currentUserService.isAdmin(user) ? null : user;
    }
}