package com.company.bookingresourses.screen.item;

import io.jmix.ui.screen.*;
import com.company.bookingresourses.entity.Item;

@UiController("Item.edit")
@UiDescriptor("item-edit.xml")
@EditedEntityContainer("itemDc")
public class ItemEdit extends StandardEditor<Item> {
}