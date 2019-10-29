package de.diedavids.cuba.metadataextensions.web;

import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.datatypes.impl.StringDatatype;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.cuba.web.AppUI;
import com.haulmont.cuba.web.sys.WebDialogs;
import de.diedavids.cuba.metadataextensions.MetadataDialogs;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;

import static com.haulmont.cuba.gui.app.core.inputdialog.InputDialog.INPUT_DIALOG_OK_ACTION;


@Component(MetadataDialogs.NAME)
public class MetadataDialogsImpl implements MetadataDialogs {

    @Override
    public MetadataInputDialogBuilder createMetadataInputDialog(FrameOwner owner) {
        return new MetadataInputDialogBuilderImpl(owner);
    }


    public class MetadataInputDialogBuilderImpl extends WebDialogs.InputDialogBuilderImpl implements MetadataInputDialogBuilder {

        private MetaProperty metaProperty;
        private Entity entityInstance;

        public MetadataInputDialogBuilderImpl(FrameOwner owner) {
            super(owner);
        }


        @Override
        public MetadataInputDialogBuilder withMetaProperty(MetaProperty property) {
            this.metaProperty = property;
            return this;
        }

        @Override
        public MetadataInputDialogBuilder withEntityInstance(Entity entityInstance) {
            this.entityInstance = entityInstance;
            return this;
        }


        @Override
        public InputDialog build() {

            if (metaProperty.getRange().isDatatype()) {
                handleDatatypeCase();
            } else if (metaProperty.getRange().isEnum()) {
                handleEnumCase();
            } else if (metaProperty.getRange().isClass()) {
                handleEntityReferenceCase();
            } else {
                handleStringCase();
            }

            return super.build();
        }

        private void handleStringCase() {
            InputParameter inputParameter = inputParameter(metaProperty)
                    .withDatatype(new StringDatatype());

            configureInputDialog(inputParameter, new StringDatatype());
        }

        private void handleEntityReferenceCase() {
            //EntitySoftReferenceDatatype datatype = new EntitySoftReferenceDatatype();
            StringDatatype datatype = new StringDatatype();

            InputParameter inputParameter = inputParameter(metaProperty)
                    .withEntityClass(metaProperty.getRange().asClass().getJavaClass());


            configureInputDialog(inputParameter, datatype);
        }

        private void handleEnumCase() {
            InputParameter inputParameter = inputParameter(metaProperty)
                    .withEnumClass(((Datatype<Object>) metaProperty.getRange().asEnumeration()).getJavaClass());

            configureInputDialog(inputParameter, metaProperty.getRange().asEnumeration());
        }

        private void handleDatatypeCase() {
            InputParameter inputParameter;
            Datatype<Object> datatype = metaProperty.getRange().asDatatype();
            inputParameter = inputParameter(metaProperty)
                    .withDatatype(datatype);

            configureInputDialog(inputParameter, datatype);
        }

        private InputParameter inputParameter(MetaProperty property) {
            return InputParameter.parameter("value")
                    .withCaption("setValueCaption");
        }

        private void configureInputDialog(
                InputParameter inputParameter,
                Datatype datatype
        ) {


            String value = entityInstance.getValue(metaProperty.getName());
            if (!StringUtils.isEmpty(value)) {
                try {
                    inputParameter.withDefaultValue(datatype.parse(value));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            this
                    .withParameter(inputParameter)
                    .withCaption("setValueCaption")
                    .withCloseListener(closeEvent -> {
                        if (closeEvent.getCloseAction().equals(INPUT_DIALOG_OK_ACTION)) {
                            Object providedDefaultValue = closeEvent.getValue("value");
                            this.entityInstance.setValue(metaProperty.getName(), datatype.format(providedDefaultValue));
                        }
                    })
                    .show();
        }
    }

}

