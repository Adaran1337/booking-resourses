package com.company.bookingresourses.screen.reservation;

import com.company.bookingresourses.app.ExpiredReservationService;
import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Reservation.browse")
@UiDescriptor("reservation-browse.xml")
@LookupComponent("reservationsTable")
public class ReservationBrowse extends StandardLookup<Reservation> {
    @Autowired
    private ExpiredReservationService expiredReservationService;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        expiredReservationService.removeExpiredReservations();
    }
}