package com.company.bookingresourses.screen.cabinet;

import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Cabinet;

@UiController("Cabinet.browse")
@UiDescriptor("cabinet-browse.xml")
@LookupComponent("cabinetsTable")
public class CabinetBrowse extends StandardLookup<Cabinet> {
}