package com.company.bookingresourses.screen.reservation;

import com.company.bookingresourses.app.ReservationService;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

@UiController("Reservation.edit")
@UiDescriptor("reservation-edit.xml")
@EditedEntityContainer("reservationDc")
public class ReservationEdit extends StandardEditor<Reservation> {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (!reservationService.compareOffices(getEditedEntity())) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption(messageBundle.getMessage("invalidOffices"))
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .show();
            event.preventCommit();
        }
    }
}