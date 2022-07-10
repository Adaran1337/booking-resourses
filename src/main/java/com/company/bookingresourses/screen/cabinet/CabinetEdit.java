package com.company.bookingresourses.screen.cabinet;

import com.company.bookingresourses.app.ResourceService;
import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Cabinet;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Cabinet.edit")
@UiDescriptor("cabinet-edit.xml")
@EditedEntityContainer("cabinetDc")
public class CabinetEdit extends StandardEditor<Cabinet> {
    @Autowired
    private ResourceService resourceService;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        getWindow().getComponentNN("officeField").setEnabled(!resourceService.isReserved(getEditedEntity()));
    }
}