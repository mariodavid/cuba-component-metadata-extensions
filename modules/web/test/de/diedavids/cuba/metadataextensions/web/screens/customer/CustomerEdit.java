package de.diedavids.cuba.metadataextensions.web.screens.customer;

import de.diedavids.cuba.metadataextensions.entity.example.Customer;
import com.haulmont.cuba.gui.screen.*;

@UiController("ceume_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
@LoadDataBeforeShow
public class CustomerEdit extends StandardEditor<Customer> {
}