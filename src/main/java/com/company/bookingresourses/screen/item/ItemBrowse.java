package com.company.bookingresourses.screen.item;

import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Item;

@UiController("Item.browse")
@UiDescriptor("item-browse.xml")
@LookupComponent("itemsTable")
public class ItemBrowse extends StandardLookup<Item> {
}