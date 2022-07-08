package com.company.bookingresourses.screen.resource;

import com.company.bookingresourses.app.ExpiredReservationService;
import com.company.bookingresourses.app.ResourcesDataGridService;
import com.company.bookingresourses.app.CurrentUserService;
import com.company.bookingresourses.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Resource;
import io.jmix.ui.screen.LookupComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


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
    @Autowired
    private ExpiredReservationService expiredReservationService;
    @Autowired
    private CollectionContainer<Resource> resourcesDc;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private CurrentUserService currentUserService;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        expiredReservationService.removeExpiredReservations();
    }

    @Subscribe("resourcesTable")
    public void onResourcesTableItemClick(DataGrid.ItemClickEvent<Resource> event) {
        resourcesTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent ->
                        resourcesTable.setDetailsVisible(resourcesTable.getSingleSelected(), true)));
    }

    @Subscribe(id = "resourcesDl", target = Target.DATA_LOADER)
    public void onResourcesDlPostLoad(CollectionLoader.PostLoadEvent<Resource> event) {
        User user = (User) currentAuthentication.getAuthentication().getPrincipal();
        if (currentUserService.isAdmin(user)) {
            return;
        }

        List<Resource> userAccessibleResources = currentUserService
                .getUserAccessibleResources(event.getLoadedEntities(), user);
        resourcesDc.setItems(userAccessibleResources);
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