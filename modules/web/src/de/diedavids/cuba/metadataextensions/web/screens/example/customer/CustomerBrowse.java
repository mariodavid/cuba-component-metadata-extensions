package de.diedavids.cuba.metadataextensions.web.screens.example.customer;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.metadataextensions.MetadataDialogs;
import de.diedavids.cuba.metadataextensions.example.entity.Customer;

import javax.inject.Inject;

import java.time.LocalDate;
import java.util.function.Function;

import static de.diedavids.cuba.metadataextensions.MetaPropertyInputParameter.metaPropertyParameter;

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
    protected CollectionContainer<Customer> customersDc;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected DataContext dataContext;
    @Inject
    protected TimeSource timeSource;

    @Subscribe("customersTable.quickChange")
    protected void onCustomersTableQuickChange(Action.ActionPerformedEvent event) {

        Customer customer = customersTable.getSingleSelected();
        MetaClass customerMetaClass = customersDc.getEntityMetaClass();

        metadataDialogs.createMetadataInputDialog(this, Customer.class)
                .withEntity(customer)
                .withCloseListener(closeEvent -> dataContext.commit())
                .withCaption(messageBundle.getMessage("quickChange"))
                .withParameters(
                        metaPropertyParameter(customerMetaClass.getProperty("name"))
                                .withAutoBinding(false),
                        metaPropertyParameter(Customer.class, "birthday")
                                .withAutoBinding(true)
                )
                .withValidator(new Function<InputDialog.ValidationContext, ValidationErrors>() {
                    @Override
                    public ValidationErrors apply(InputDialog.ValidationContext validationContext) {
                        LocalDate birthday = validationContext
                                .getValue("birthday");

                        if (birthday.isAfter(tenYearsAgo())) {
                            return ValidationErrors.of("Customer is too young");
                        }
                        else {
                            return ValidationErrors.none();
                        }
                    }
                })
                .show();
    }

    private LocalDate tenYearsAgo() {
        return timeSource.now().toLocalDate().minusYears(10);
    }

}