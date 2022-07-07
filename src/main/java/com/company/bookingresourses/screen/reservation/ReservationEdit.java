package com.company.bookingresourses.screen.reservation;

import com.company.bookingresourses.app.ReservationService;
import com.company.bookingresourses.app.ResourcesDataGridService;
import com.company.bookingresourses.entity.Resource;
import com.company.bookingresourses.entity.User;
import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Reservation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

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
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ResourcesDataGridService<Resource> resourcesDataGridService;


    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (!reservationService.compareOffices(getEditedEntity())) {
            showNotification("invalidOffices", "invalidOfficesDescription");
            event.preventCommit();
        } else if (!reservationService.checkTime(getEditedEntity()) ||
                reservationService.timeIntersection(getEditedEntity())) {
            showNotification("wrongTime", "wrongTimeDescription");
            event.preventCommit();
        }
    }

    private void showNotification(String captionPath, String descriptionPath) {
        notifications.create(Notifications.NotificationType.ERROR)
                .withCaption(messageBundle.getMessage(captionPath))
                .withDescription(messageBundle.getMessage(descriptionPath))
                .withPosition(Notifications.Position.BOTTOM_RIGHT)
                .show();
    }

    @Subscribe("resourcesField")
    public void onResourcesFieldValueChange(HasValue.ValueChangeEvent<Resource> event) {
        if (event.getPrevValue() != null && event.getValue() != null) {
            UUID previousId = event.getPrevValue().getId();
            UUID currentId = event.getValue().getId();

            if (previousId.compareTo(currentId) == 0) {
                return;
            }
        }

        VBoxLayout mainLayout = resourcesDataGridService.createVBoxLayoutDetails(uiComponents, event.getValue());

        VBoxLayout resourceReservations = (VBoxLayout) getWindow().getComponentNN("resourceReservations");
        resourceReservations.removeAll();
        resourceReservations.add(mainLayout);
    }
}