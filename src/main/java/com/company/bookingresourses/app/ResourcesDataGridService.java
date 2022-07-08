package com.company.bookingresourses.app;

import com.company.bookingresourses.entity.Reservation;
import com.company.bookingresourses.entity.Resource;
import com.company.bookingresourses.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.Messages;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class ResourcesDataGridService<T extends Resource> {
    @Autowired
    private Messages messages;
    private final String baseMessagePath = "com.company.bookingresourses.app.ResourcesDataGridService/";
    @Autowired
    private DataManager dataManager;

    public Component tableDetailsGenerator(UiComponents uiComponents, DataGrid<T> table, T entity) {
        Component closeButton = createCloseButton(uiComponents, table, entity);
        return createVBoxLayoutDetails(uiComponents, entity, closeButton);
    }

    public VBoxLayout createVBoxLayoutDetails(UiComponents uiComponents, T entity, Component ... components) {
        VBoxLayout mainLayout = uiComponents.create(VBoxLayout.class);
        mainLayout.setWidth("100%");
        mainLayout.setMargin(true);

        HBoxLayout headerBox = uiComponents.create(HBoxLayout.class);
        headerBox.setWidth("100%");

        Label<String> infoLabel = uiComponents.create(Label.TYPE_STRING);
        infoLabel.setHtmlEnabled(true);
        infoLabel.setStyleName("h2");
        infoLabel.setValue(messages.getMessage(baseMessagePath + "reservation"));

        headerBox.add(infoLabel);
        headerBox.expand(infoLabel);

        for (Component component : components) {
            headerBox.add(component);
        }

        mainLayout.add(headerBox);
        if (entity == null) {
            return mainLayout;
        }

        Component content = getContent(uiComponents, entity);

        mainLayout.add(content);
        mainLayout.expand(content);

        return mainLayout;
    }

    private Component createCloseButton(UiComponents uiComponents, DataGrid<T> table, T entity) {
        Button closeButton = uiComponents.create(Button.class);
        closeButton.setIcon("font-icon:TIMES");
        BaseAction closeAction = new BaseAction("closeAction")
                .withHandler(actionPerformedEvent ->
                        table.setDetailsVisible(entity, false))
                .withCaption("");
        closeButton.setAction(closeAction);
        return closeButton;
    }

    public Component getContent(UiComponents uiComponents, T entity) {
        Label<String> content = uiComponents.create(Label.TYPE_STRING);
        content.setHtmlEnabled(true);
        content.setId("contentLabel");

        if (entity == null) {
            return content;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        if (entity.getReservations().size() == 0) {
            sb.append("<b>").append(messages.getMessage(baseMessagePath + "missingReservation")).append("</b>");
        }

        for (int i = 0; i < entity.getReservations().size(); i++) {
            Reservation reservation = entity.getReservations().get(i);
            UUID userId = reservation.getEmployee().getId();
            User user = dataManager.load(User.class).id(userId).one();
            sb.append("<b>").append(i + 1).append(":</b> ")
                    .append(user.getDisplayName())
                    .append(" ").append(messages.getMessage(baseMessagePath + "reservedFrom")).append(" ")
                    .append(reservation.getStartDate().format(formatter))
                    .append(" ").append(messages.getMessage(baseMessagePath + "reservedTo")).append(" ")
                    .append(reservation.getEndDate().format(formatter)).append("<br><br>");
        }

        content.setValue(sb.toString());

        return content;
    }
}