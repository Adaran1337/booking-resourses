package com.company.bookingresourses.screen.reservation;

import com.company.bookingresourses.app.CurrentUserService;
import com.company.bookingresourses.app.ExpiredReservationService;
import com.company.bookingresourses.app.ReservationService;
import com.company.bookingresourses.entity.Resource;
import com.company.bookingresourses.entity.User;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UiController("Reservation.browse")
@UiDescriptor("reservation-browse.xml")
@LookupComponent("reservationsTable")
public class ReservationBrowse extends StandardLookup<Reservation> {
    @Autowired
    private ExpiredReservationService expiredReservationService;
    @Autowired
    private GroupTable<Reservation> reservationsTable;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private CollectionContainer<Reservation> reservationsDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        expiredReservationService.removeExpiredReservations();
    }

    @Subscribe(id = "reservationsDl", target = Target.DATA_LOADER)
    public void onReservationsDlPostLoad(CollectionLoader.PostLoadEvent<Reservation> event) {
        User user = currentUserService.getCurrentUser();
        if (currentUserService.isAdmin(user)) {
            return;
        }

        List<Reservation> userAccessibleReservations = currentUserService
                .getUserAccessibleReservations(event.getLoadedEntities(), user);
        reservationsDc.setItems(userAccessibleReservations);
    }

    @Install(to = "reservationsTable.remove", subject = "enabledRule")
    private boolean reservationsTableRemoveEnabledRule() {
        Set<Reservation> reservations = reservationsTable.getSelected();
        return reservationService.canActionBePerformed(reservations);
    }

    @Install(to = "reservationsTable.edit", subject = "enabledRule")
    private boolean reservationsTableEditEnabledRule() {
        Set<Reservation> reservations = reservationsTable.getSelected();
        return reservationService.canActionBePerformed(reservations);
    }



}