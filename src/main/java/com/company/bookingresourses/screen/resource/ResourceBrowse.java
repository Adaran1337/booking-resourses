package com.company.bookingresourses.screen.resource;

import com.company.bookingresourses.entity.Reservation;
import com.company.bookingresourses.entity.User;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Resource;
import io.jmix.ui.screen.LookupComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

@UiController("Resource.browse")
@UiDescriptor("resource-browse.xml")
@LookupComponent("resourcesTable")
public class ResourceBrowse extends StandardLookup<Resource> {
    @Autowired
    private DataGrid<Resource> resourcesTable;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe("resourcesTable")
    public void onResourcesTableItemClick(DataGrid.ItemClickEvent<Resource> event) {
        resourcesTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent ->
                        resourcesTable.setDetailsVisible(resourcesTable.getSingleSelected(), true)));
    }

    @Install(to = "resourcesTable", subject = "detailsGenerator")
    private Component resourcesTableDetailsGenerator(Resource resource) {
        VBoxLayout mainLayout = uiComponents.create(VBoxLayout.class);
        mainLayout.setWidth("100%");
        mainLayout.setMargin(true);

        HBoxLayout headerBox = uiComponents.create(HBoxLayout.class);
        headerBox.setWidth("100%");

        Label<String> infoLabel = uiComponents.create(Label.TYPE_STRING);
        infoLabel.setHtmlEnabled(true);
        infoLabel.setStyleName("h2");
        infoLabel.setValue(messageBundle.getMessage("reservation"));

        Component closeButton = createCloseButton(resource);
        headerBox.add(infoLabel);
        headerBox.add(closeButton);
        headerBox.expand(infoLabel);

        Component content = getContent(resource);

        mainLayout.add(headerBox);
        mainLayout.add(content);
        mainLayout.expand(content);

        return mainLayout;
    }

    protected Component createCloseButton(Resource entity) {
        Button closeButton = uiComponents.create(Button.class);
        closeButton.setIcon("font-icon:TIMES");
        BaseAction closeAction = new BaseAction("closeAction")
                .withHandler(actionPerformedEvent ->
                        resourcesTable.setDetailsVisible(entity, false))
                .withCaption("");
        closeButton.setAction(closeAction);
        return closeButton;
    }

    protected Component getContent(Resource entity) {
        Label<String> content = uiComponents.create(Label.TYPE_STRING);
        content.setHtmlEnabled(true);
        content.setId("contentLabel");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        if (entity.getReservations().size() == 0) {
            sb.append("<b>").append(messageBundle.getMessage("missingReservation")).append("</b>");
        }

        for (int i = 0; i < entity.getReservations().size(); i++) {
            Reservation reservation = entity.getReservations().get(i);
            User user = reservation.getEmployee();
            sb.append("<b>").append(i + 1).append(":</b> ")
                    .append(user.getUsername())
                    .append(" ").append(messageBundle.getMessage("reservedFrom")).append(" ")
                    .append(reservation.getStartDate().format(formatter))
                    .append(" ").append(messageBundle.getMessage("reservedTo")).append(" ")
                    .append(reservation.getEndDate().format(formatter)).append("<br><br>");
        }

        content.setValue(sb.toString());

        return content;
    }

    @Install(to = "resourcesTable", subject = "rowStyleProvider")
    private String resourcesTableRowStyleProvider(Resource resource) {
        return resource.getReservations().size() > 0 ? "reserved-resource" : null;
    }
}