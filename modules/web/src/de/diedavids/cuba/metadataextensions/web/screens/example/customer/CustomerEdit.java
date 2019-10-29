package de.diedavids.cuba.metadataextensions.web.screens.example.customer;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.metadataextensions.example.entity.Customer;

@UiController("ddcme_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
@LoadDataBeforeShow
public class CustomerEdit extends StandardEditor<Customer> {
}