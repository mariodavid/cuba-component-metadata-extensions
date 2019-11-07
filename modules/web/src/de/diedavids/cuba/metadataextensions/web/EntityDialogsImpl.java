package de.diedavids.cuba.metadataextensions.web;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.cuba.web.AppUI;
import de.diedavids.cuba.metadataextensions.EntityAttributeInputParameter;
import de.diedavids.cuba.metadataextensions.EntityDialogs;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.haulmont.cuba.gui.app.core.inputdialog.InputDialog.INPUT_DIALOG_OK_ACTION;


@Component(EntityDialogs.NAME)
public class EntityDialogsImpl implements EntityDialogs {

    @Override
    public <E extends Entity> EntityInputDialogBuilder<E> createEntityInputDialog(FrameOwner frameOwner, Class<E> entityClass) {
        return new EntityInputDialogBuilderImpl<E>(frameOwner, entityClass);
    }


    private Dialogs getDialogs() {
        return AppUI.getCurrent().getDialogs();
    }


    public class EntityInputDialogBuilderImpl<E extends Entity> implements EntityInputDialogBuilder<E> {

        private E entityInstance;
        private String caption;
        private Consumer<InputDialog.InputDialogCloseEvent> listener;
        private List<EntityAttributeInputParameter> parameters = new ArrayList<>(2);

        private final Dialogs.InputDialogBuilder inputDialog;
        private final Class<? extends Entity> entityClass;

        public <E extends Entity> EntityInputDialogBuilderImpl(FrameOwner frameOwner, Class<E> entityClass) {
            inputDialog = getDialogs().createInputDialog(frameOwner);
            this.entityClass = entityClass;
        }

        @Override
        public EntityInputDialogBuilder withParameter(EntityAttributeInputParameter inputParameter) {
            parameters.add(inputParameter);
            return this;
        }

        @Override
        public EntityInputDialogBuilder withParameters(EntityAttributeInputParameter... inputParameters) {
            parameters.addAll(Arrays.asList(inputParameters));
            return this;
        }

        @Override
        public EntityInputDialogBuilder withEntity(E entity) {
            this.entityInstance = entity;
            return this;
        }

        @Override
        public EntityInputDialogBuilder withCaption(String caption) {
            this.caption = caption;
            return this;
        }

        @Override
        public EntityInputDialogBuilder withCloseListener(Consumer<InputDialog.InputDialogCloseEvent> listener) {
            this.listener = listener;
            return this;
        }

        @Override
        public EntityInputDialogBuilder withValidator(Function<InputDialog.ValidationContext, ValidationErrors> validator) {
            inputDialog.withValidator(validator);
            return this;
        }

        @Override
        public EntityInputDialogBuilder withWidth(String width) {
            inputDialog.withWidth(width);
            return this;
        }

        @Override
        public EntityInputDialogBuilder withHeight(String height) {
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
                    .map(EntityAttributeInputParameter::getInputParameter)
                    .toArray(InputParameter[]::new);
        }

        private void assignDefaultValuesToAutoBindingParameters() {
            parametersWithAutoBinding()
                    .forEach(
                            entityAttributeInputParameter -> entityAttributeInputParameter.withDefaultValue(
                                    entityInstance.getValue(entityAttributeInputParameter.getMetaProperty().getName())
                            )
                    );
        }

        private Stream<EntityAttributeInputParameter> parametersWithAutoBinding() {
            return parameters.stream()
                    .filter(EntityAttributeInputParameter::isAutoBinding);
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
                    .map(entityAttributeInputParameter -> entityAttributeInputParameter.getMetaProperty().getName())
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

