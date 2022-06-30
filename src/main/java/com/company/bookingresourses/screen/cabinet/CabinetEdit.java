package com.company.bookingresourses.screen.cabinet;

import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Cabinet;

@UiController("Cabinet.edit")
@UiDescriptor("cabinet-edit.xml")
@EditedEntityContainer("cabinetDc")
public class CabinetEdit extends StandardEditor<Cabinet> {
}