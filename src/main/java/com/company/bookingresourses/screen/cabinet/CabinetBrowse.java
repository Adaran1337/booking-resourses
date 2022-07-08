package com.company.bookingresourses.screen.cabinet;

import com.company.bookingresourses.app.ExpiredReservationService;
import com.company.bookingresourses.app.ResourcesDataGridService;
import com.company.bookingresourses.app.CurrentUserService;
import com.company.bookingresourses.entity.User;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Cabinet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("Cabinet.browse")
@UiDescriptor("cabinet-browse.xml")
@LookupComponent("cabinetsTable")
public class CabinetBrowse extends StandardLookup<Cabinet> {
    @Autowired
    private DataGrid<Cabinet> cabinetsTable;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ResourcesDataGridService<Cabinet> resourcesDataGridService;
    @Autowired
    private ExpiredReservationService expiredReservationService;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private CollectionContainer<Cabinet> cabinetsDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        expiredReservationService.removeExpiredReservations();
    }

    @Subscribe(id = "cabinetsDl", target = Target.DATA_LOADER)
    public void onCabinetsDlPostLoad(CollectionLoader.PostLoadEvent<Cabinet> event) {
        User user = currentUserService.getCurrentUser();
        if (currentUserService.isAdmin(user)) {
            return;
        }

        List<Cabinet> userAccessibleResources = currentUserService
                .getUserAccessibleCabinets(event.getLoadedEntities(), user);
        cabinetsDc.setItems(userAccessibleResources);
    }

    @Subscribe("cabinetsTable")
    public void onCabinetsTableItemClick(DataGrid.ItemClickEvent<Cabinet> event) {
        cabinetsTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent ->
                        cabinetsTable.setDetailsVisible(cabinetsTable.getSingleSelected(), true)));
    }

    @Install(to = "cabinetsTable", subject = "detailsGenerator")
    private Component resourcesTableDetailsGenerator(Cabinet cabinet) {
        return resourcesDataGridService.tableDetailsGenerator(uiComponents, cabinetsTable, cabinet);
    }

    @Install(to = "cabinetsTable", subject = "rowStyleProvider")
    private String cabinetsTableRowStyleProvider(Cabinet cabinet) {
        return cabinet.getReservations().size() > 0 ? "reserved-resource" : null;
    }
}