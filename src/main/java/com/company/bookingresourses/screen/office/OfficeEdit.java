package com.company.bookingresourses.screen.office;

import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Office;

@UiController("Office.edit")
@UiDescriptor("office-edit.xml")
@EditedEntityContainer("officeDc")
public class OfficeEdit extends StandardEditor<Office> {
}