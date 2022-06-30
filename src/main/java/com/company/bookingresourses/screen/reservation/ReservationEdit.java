package com.company.bookingresourses.screen.reservation;

import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Reservation;

@UiController("Reservation.edit")
@UiDescriptor("reservation-edit.xml")
@EditedEntityContainer("reservationDc")
public class ReservationEdit extends StandardEditor<Reservation> {
}