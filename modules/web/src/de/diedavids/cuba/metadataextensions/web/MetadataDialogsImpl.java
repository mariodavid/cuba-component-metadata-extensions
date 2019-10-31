package de.diedavids.cuba.metadataextensions.web;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.cuba.web.AppUI;
import de.diedavids.cuba.metadataextensions.MetaPropertyInputParameter;
import de.diedavids.cuba.metadataextensions.MetadataDialogs;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.haulmont.cuba.gui.app.core.inputdialog.InputDialog.INPUT_DIALOG_OK_ACTION;


@Component(MetadataDialogs.NAME)
public class MetadataDialogsImpl implements MetadataDialogs {

    @Override
    public <E extends Entity> MetadataInputDialogBuilder<E> createMetadataInputDialog(FrameOwner frameOwner, Class<E> entityClass) {
        return new MetadataInputDialogBuilderImpl<E>(frameOwner, entityClass);
    }


    private Dialogs getDialogs() {
        return AppUI.getCurrent().getDialogs();
    }


    public class MetadataInputDialogBuilderImpl<E extends Entity> implements MetadataInputDialogBuilder<E> {

        private E entityInstance;
        private String caption;
        private Consumer<InputDialog.InputDialogCloseEvent> listener;
        private List<MetaPropertyInputParameter> parameters = new ArrayList<>(2);

        private final Dialogs.InputDialogBuilder inputDialog;
        private final Class<? extends Entity> entityClass;

        public <E extends Entity> MetadataInputDialogBuilderImpl(FrameOwner frameOwner, Class<E> entityClass) {
            inputDialog = getDialogs().createInputDialog(frameOwner);
            this.entityClass = entityClass;
        }

        @Override
        public MetadataInputDialogBuilder withParameter(MetaPropertyInputParameter inputParameter) {
            parameters.add(inputParameter);
            return this;
        }

        @Override
        public MetadataInputDialogBuilder withParameters(MetaPropertyInputParameter... inputParameters) {
            parameters.addAll(Arrays.asList(inputParameters));
            return this;
        }

        @Override
        public MetadataInputDialogBuilder withEntity(E entity) {
            this.entityInstance = entity;
            return this;
        }

        @Override
        public MetadataInputDialogBuilder withCaption(String caption) {
            this.caption = caption;
            return this;
        }

        @Override
        public MetadataInputDialogBuilder withCloseListener(Consumer<InputDialog.InputDialogCloseEvent> listener) {
            this.listener = listener;
            return this;
        }

        @Override
        public MetadataInputDialogBuilder withValidator(Function<InputDialog.ValidationContext, ValidationErrors> validator) {
            inputDialog.withValidator(validator);
            return this;
        }

        @Override
        public MetadataInputDialogBuilder withWidth(String width) {
            inputDialog.withWidth(width);
            return this;
        }

        @Override
        public MetadataInputDialogBuilder withHeight(String height) {
            inputDialog.withHeight(height);
            return this;
        }


        @Override
        public InputDialog build() {

            if (entityInstance != null) {
                assignDefaultValuesToAutoBindingParameters();
            }

            return inputDialog
                    .withParameters(convertParameters())
                    .withCaption(caption)
                    .withCloseListener(closeListener())
                    .build();
        }

        private InputParameter[] convertParameters() {
            return parameters
                    .stream()
                    .map(MetaPropertyInputParameter::getInputParameter)
                    .toArray(InputParameter[]::new);
        }

        private void assignDefaultValuesToAutoBindingParameters() {
            parametersWithAutoBinding()
                    .forEach(
                            metaPropertyInputParameter -> metaPropertyInputParameter.withDefaultValue(
                                    entityInstance.getValue(metaPropertyInputParameter.getMetaProperty().getName())
                            )
                    );
        }

        private Stream<MetaPropertyInputParameter> parametersWithAutoBinding() {
            return parameters.stream()
                    .filter(MetaPropertyInputParameter::isAutoBinding);
        }

        private Consumer<InputDialog.InputDialogCloseEvent> closeListener() {

            if (entityInstance != null) {
                return closeEvent -> {
                    if (closeEvent.getCloseAction().equals(INPUT_DIALOG_OK_ACTION)) {
                        updateEntityInstanceValues(closeEvent.getValues());
                    }
                    if (listener != null) {
                        listener.accept(closeEvent);
                    }
                };
            } else {
                return listener;
            }
        }

        private void updateEntityInstanceValues(Map<String, Object> values) {
            parametersWithAutoBinding()
                    .map(metaPropertyInputParameter -> metaPropertyInputParameter.getMetaProperty().getName())
                    .forEach(parameterName ->
                            entityInstance.setValue(parameterName, values.get(parameterName))
                    );
        }

        @Override
        public InputDialog show() {
            InputDialog dialog = build();
            dialog.show();
            return dialog;
        }

    }

}

