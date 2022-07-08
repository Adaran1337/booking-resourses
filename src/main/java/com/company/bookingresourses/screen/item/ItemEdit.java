package com.company.bookingresourses.screen.item;

import com.company.bookingresourses.app.ResourceService;
import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Item.edit")
@UiDescriptor("item-edit.xml")
@EditedEntityContainer("itemDc")
public class ItemEdit extends StandardEditor<Item> {
    @Autowired
    private ResourceService resourceService;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        getWindow().getComponentNN("officeField").setEnabled(!resourceService.isReserved(getEditedEntity()));
    }
}