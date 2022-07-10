package com.company.bookingresourses.app;

import com.company.bookingresourses.entity.Reservation;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpiredReservationService {
    @Autowired
    private DataManager dataManager;

    public void removeExpiredReservations() {
        List<Reservation> reservations = dataManager.load(Reservation.class)
                .condition(PropertyCondition.equal("deletedDate", null))
                .list();

        List<Reservation> expiredReservations = reservations.stream()
                .filter(reservation -> reservation.getEndDate().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());

        dataManager.remove(expiredReservations);
    }
}