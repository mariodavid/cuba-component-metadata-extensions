package de.diedavids.cuba.metadataextensions;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.screen.FrameOwner;

import java.util.function.Consumer;
import java.util.function.Function;


public interface MetadataDialogs {


    String NAME = "ddcme_MetadataDialogs";

    /**
     * Creates a metadata input dialog builder. This input dialog renders
     * input fields for properties of an entity
     *
     * <p>
     * Example of showing an input dialog:
     * <pre>{@code
     * metadataDialogs.createMetadataInputDialog(this, Customer.class)
     *     .withCaption(messageBundle.getMessage("change"))
     *     .withParameters(
     *             metaPropertyParameter(customerMetaClass.getProperty("name"))
     *                     .withAutoBinding(false),
     *             metaPropertyParameter(Customer.class, "birthday")
     *                     .withAutoBinding(true)
     *     )
     *     .withEntity(customer)
     *     .withCloseListener(closeEvent -> dataContext.commit())
     *     .show();
     * }</pre>
     *
     * @param frameOwner origin screen from input dialog is invoked
     * @return builder
     */
    <E extends Entity> MetadataInputDialogBuilder<E> createMetadataInputDialog(FrameOwner frameOwner, Class<E> entityClass);




    /**
     * Builder for dialogs with inputs of meta properties.
     */
    interface MetadataInputDialogBuilder<E extends Entity> {


        /**
         * adds a {@link MetaPropertyInputParameter} for the input dialog
         *
         * @param inputParameter the input parameter
         * @return builder
         */
        MetadataInputDialogBuilder withParameter(MetaPropertyInputParameter inputParameter);

        /**
         * adds multiple {@link MetaPropertyInputParameter} for the input dialog
         *
         * @param inputParameters the input parameters
         * @return builder
         */
        MetadataInputDialogBuilder withParameters(MetaPropertyInputParameter... inputParameters);

        /**
         * sets the entity instance to use for displaying the value and updating the entity instance
         *
         * @param entityInstance the entity instance to use
         * @return builder
         */
        MetadataInputDialogBuilder withEntity(E entityInstance);

        /**
         * sets the caption of the input dialog
         *
         * @param caption the caption
         * @return builder
         */
        MetadataInputDialogBuilder withCaption(String caption);

        /**
         * Add close listener to the dialog. See close actions for {@link DialogActions} in {@link InputDialog}.
         *
         * @param listener
         * @return builder
         */
        MetadataInputDialogBuilder withCloseListener(Consumer<InputDialog.InputDialogCloseEvent> listener);


        /**
         * Sets additional handler for field validation. It receives input dialog context and must return {@link ValidationErrors}
         * instance. Returned validation errors will be shown with another errors from fields.
         * Example
         * <pre>{@code
         *  metadataDialogs.createMetadataInputDialog(this, Customer.class)
         *         .withParameters(
         *             metaPropertyParameter(Customer.class, "name")
         *                     .withAutoBinding(true),
         *             metaPropertyParameter(Customer.class, "birthday")
         *                     .withAutoBinding(true)
         *         .withValidator(context -> {
         *             String name = context.getValue("name");
         *             LocalDate birthday = context.getValue("birthday");
         *             if (Strings.isNullOrEmpty(name) && Strings.isNullOrEmpty(birthday)) {
         *                 return ValidationErrors.of("Name and Birthday have to be filled");
         *             }
         *             return ValidationErrors.none();
         *         })
         *         .show();
         *  }</pre>
         *
         * @param validator validator
         * @return builder
         */
        MetadataInputDialogBuilder withValidator(Function<InputDialog.ValidationContext, ValidationErrors> validator);

        /**
         * Sets dialog width.
         *
         * @param width dialog width
         * @return builder
         */
        MetadataInputDialogBuilder withWidth(String width);

        /**
         * Sets dialog height.
         *
         * @param height dialog height
         * @return builder
         */
        MetadataInputDialogBuilder withHeight(String height);

        /**
         * Shows the dialog.
         *
         * @return opened input dialog
         */
        InputDialog show();

        /**
         * Builds the input dialog.
         *
         * @return input dialog
         */
        InputDialog build();

    }
}
