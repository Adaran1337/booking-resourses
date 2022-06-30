package com.company.bookingresourses.screen.office;

import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Office;

@UiController("Office.browse")
@UiDescriptor("office-browse.xml")
@LookupComponent("officesTable")
public class OfficeBrowse extends StandardLookup<Office> {
}