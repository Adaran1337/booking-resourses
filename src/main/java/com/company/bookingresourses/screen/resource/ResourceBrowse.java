package com.company.bookingresourses.screen.resource;

import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Resource;

@UiController("Resource.browse")
@UiDescriptor("resource-browse.xml")
@LookupComponent("resourcesTable")
public class ResourceBrowse extends StandardLookup<Resource> {
}