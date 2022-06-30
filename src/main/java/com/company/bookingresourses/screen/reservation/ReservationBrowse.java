package com.company.bookingresourses.screen.reservation;

import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Reservation;

@UiController("Reservation.browse")
@UiDescriptor("reservation-browse.xml")
@LookupComponent("reservationsTable")
public class ReservationBrowse extends StandardLookup<Reservation> {
}