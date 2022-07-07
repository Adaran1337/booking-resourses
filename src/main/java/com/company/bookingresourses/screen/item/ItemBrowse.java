package com.company.bookingresourses.screen.item;

import com.company.bookingresourses.app.ResourcesDataGridService;
import com.company.bookingresourses.entity.Cabinet;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Item.browse")
@UiDescriptor("item-browse.xml")
@LookupComponent("itemsTable")
public class ItemBrowse extends StandardLookup<Item> {
    @Autowired
    private DataGrid<Item> itemsTable;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ResourcesDataGridService<Item> resourcesDataGridService;

    @Subscribe("itemsTable")
    public void onItemsTableItemClick(DataGrid.ItemClickEvent<Item> event) {
        itemsTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent ->
                        itemsTable.setDetailsVisible(itemsTable.getSingleSelected(), true)));
    }

    @Install(to = "itemsTable", subject = "detailsGenerator")
    private Component resourcesTableDetailsGenerator(Item item) {
        return resourcesDataGridService.tableDetailsGenerator(uiComponents, itemsTable, item);
    }

    @Install(to = "itemsTable", subject = "rowStyleProvider")
    private String itemsTableRowStyleProvider(Item item) {
        return item.getReservations().size() > 0 ? "reserved-resource" : null;
    }
}