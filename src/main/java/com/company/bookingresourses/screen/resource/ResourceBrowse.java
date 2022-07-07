package com.company.bookingresourses.screen.resource;

import com.company.bookingresourses.app.ResourcesDataGridService;
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
    private ResourcesDataGridService<Resource> resourcesDataGridService;

    @Subscribe("resourcesTable")
    public void onResourcesTableItemClick(DataGrid.ItemClickEvent<Resource> event) {
        resourcesTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent ->
                        resourcesTable.setDetailsVisible(resourcesTable.getSingleSelected(), true)));
    }

    @Install(to = "resourcesTable", subject = "detailsGenerator")
    private Component resourcesTableDetailsGenerator(Resource resource) {
        return resourcesDataGridService.tableDetailsGenerator(uiComponents, resourcesTable, resource);
    }

    @Install(to = "resourcesTable", subject = "rowStyleProvider")
    private String resourcesTableRowStyleProvider(Resource resource) {
        return resource.getReservations().size() > 0 ? "reserved-resource" : null;
    }
}