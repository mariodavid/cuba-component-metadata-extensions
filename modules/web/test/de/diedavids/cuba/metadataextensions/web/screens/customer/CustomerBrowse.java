package de.diedavids.cuba.metadataextensions.web.screens.customer;

import de.diedavids.cuba.metadataextensions.entity.example.Customer;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.metadataextensions.EntityDialogs;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.function.Function;

import static de.diedavids.cuba.metadataextensions.EntityAttributeInputParameter.entityAttributeParameter;

@UiController("ceume_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
@LoadDataBeforeShow
public class CustomerBrowse extends StandardLookup<Customer> {


    @Inject
    protected EntityDialogs metadataDialogs;
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

        metadataDialogs.createEntityInputDialog(this, Customer.class)
                .withEntity(customer)
                .withCaption(messageBundle.getMessage("quickChange"))
                .withParameters(
                        entityAttributeParameter(Customer.class, "name")
                                .withAutoBinding(true),
                        entityAttributeParameter(Customer.class, "birthday")
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
                .withCloseListener(closeEvent -> dataContext.commit())
                .show();
    }

    private LocalDate tenYearsAgo() {
        return timeSource.now().toLocalDate().minusYears(10);
    }
}