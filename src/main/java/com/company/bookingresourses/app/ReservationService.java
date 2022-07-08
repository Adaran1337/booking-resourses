package com.company.bookingresourses.app;

import com.company.bookingresourses.entity.Reservation;
import com.company.bookingresourses.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private CurrentAuthentication currentAuthentication;

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

    public User getCurrentEmployee() {
        User employee = (User) currentAuthentication.getAuthentication().getPrincipal();
        List<String> roles = employee.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return roles.contains("system-full-access") ? null : employee;
    }
}