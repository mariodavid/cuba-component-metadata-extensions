package de.diedavids.cuba.metadataextensions.web.screens.example.customer;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.metadataextensions.MetadataDialogs;
import de.diedavids.cuba.metadataextensions.example.entity.Customer;

import javax.inject.Inject;

@UiController("ddcme_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
@LoadDataBeforeShow
public class CustomerBrowse extends StandardLookup<Customer> {

    @Inject
    protected MetadataDialogs metadataDialogs;

    @Inject
    protected GroupTable<Customer> customersTable;
    @Inject
    protected Metadata metadata;
    @Inject
    protected CollectionContainer<Customer> customersDc;

    @Subscribe("customersTable.setName")
    protected void onCustomersTableSetName(Action.ActionPerformedEvent event) {

        MetadataDialogs.MetadataInputDialogBuilder metadataInputDialog = metadataDialogs.createMetadataInputDialog(this);


        metadataInputDialog.withMetaProperty(customersDc.getEntityMetaClass().getProperty("name"));
        metadataInputDialog.withEntityInstance(customersTable.getSingleSelected());

        metadataInputDialog.show();
    }
    


    
}