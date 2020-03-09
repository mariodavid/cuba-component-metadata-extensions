package de.diedavids.cuba.metadataextensions.web.screens.example;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.InputDialogFacet;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.security.entity.User;
import de.diedavids.cuba.metadataextensions.EntityAttributeInputParameter;
import de.diedavids.cuba.metadataextensions.EntityDialogFacet;
import de.diedavids.cuba.metadataextensions.EntityDialogs;

import javax.inject.Inject;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@UiController("ddcme_EntityDialogFacetExample")
@UiDescriptor("entity-dialog-facet-example.xml")
public class EntityDialogFacetExample extends Screen {


    @Inject
    protected EntityDialogFacet createUser;
    @Inject
    protected Notifications notifications;
    @Inject
    protected EntityDialogs entityDialogs;


    @Subscribe("createUser")
    public void onInputDialogClose(EntityDialogFacet.CloseEvent closeEvent) {
        renderValues(closeEvent.getValues());
    }

    private void renderValues(Map<String, Object> resultValues) {
        String values = resultValues.entrySet()
                .stream()
                .map(entry -> String.format("%s = %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));

        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption("InputDialog Closed")
                .withDescription("Values: " + values)
                .show();
    }

    @Subscribe("openCreateUserProgrammatically")
    protected void onOpenCreateUserProgrammatically(Action.ActionPerformedEvent event) {
        EntityDialogs.EntityInputDialogBuilder<User> builder = entityDialogs.createEntityInputDialog(
                this, User.class
        );

        builder
                .withCaption("Create User Programmatically")
                .withParameter(
                        EntityAttributeInputParameter.entityAttributeParameter(User.class, "login")
                        .withAutoBinding(true)
                )
                .withCloseListener(new Consumer<InputDialog.InputDialogCloseEvent>() {
                    @Override
                    public void accept(InputDialog.InputDialogCloseEvent inputDialogCloseEvent) {
                            renderValues(inputDialogCloseEvent.getValues());
                    }
                })
                .show();
    }




}