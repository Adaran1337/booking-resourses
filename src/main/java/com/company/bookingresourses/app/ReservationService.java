package com.company.bookingresourses.app;

import com.company.bookingresourses.entity.Reservation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationService {
    public boolean compareOffices(Reservation reservation) {
        UUID employeeOffice = reservation.getEmployee().getOffice().getId();
        UUID resourceOffice = reservation.getResources().getOffice().getId();
        return employeeOffice.equals(resourceOffice);
    }
}