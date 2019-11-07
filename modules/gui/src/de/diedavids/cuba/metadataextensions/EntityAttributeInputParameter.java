package de.diedavids.cuba.metadataextensions;

import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.MessageTools;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;

/**
 * describes a parameter used in {@link EntityDialogs.EntityInputDialogBuilder}
 */
public class EntityAttributeInputParameter {



    /**
     * Creates meta property input parameter
     *
     * @param metaProperty the {@link MetaProperty} instance
     * @return meta property input parameter
     */
    public static EntityAttributeInputParameter entityAttributeParameter(MetaProperty metaProperty) {
        return new EntityAttributeInputParameter(metaProperty.getName())
                .withMetaProperty(metaProperty);
    }


    /**
     * Creates meta property input parameter
     *
     * @param entityClass the class of the entity
     * @param property the property of the entity
     * @return meta property input parameter
     */
    public static EntityAttributeInputParameter entityAttributeParameter(Class<?> entityClass, String property) {
        MetaProperty metaProperty = getMetadata().getClass(entityClass).getProperty(property);
        return entityAttributeParameter(metaProperty);
    }


    /**
     * Creates meta property input parameter
     *
     * @param entityClass the class name of the entity
     * @param property the property of the entity
     * @return meta property input parameter
     */
    public static EntityAttributeInputParameter entityAttributeParameter(String entityClass, String property) {
        MetaProperty metaProperty = getMetadata().getClass(entityClass).getProperty(property);
        return entityAttributeParameter(metaProperty);
    }



    private InputParameter inputParameter;
    private boolean autoBinding;
    private MetaProperty metaProperty;


    private EntityAttributeInputParameter(String id) {
        inputParameter = InputParameter.parameter(id);
    }



    public EntityAttributeInputParameter withMetaProperty(MetaProperty metaProperty) {

        this.metaProperty = metaProperty;
        setInputParameterType(metaProperty);

        this.withRequired(metaProperty.isMandatory());
        this.withCaption(getMessageTools().getPropertyCaption(metaProperty));

        return this;
    }

    private void setInputParameterType(MetaProperty metaProperty) {
        if (metaProperty.getRange().isDatatype()) {
            inputParameter
                    .withDatatype(metaProperty.getRange().asDatatype());
        } else if (metaProperty.getRange().isEnum()) {
            inputParameter
                    .withEnumClass(((Datatype<Object>) metaProperty.getRange().asEnumeration()).getJavaClass());
        } else if (metaProperty.getRange().isClass()) {
            inputParameter
                    .withEntityClass(metaProperty.getRange().asClass().getJavaClass());
        }
    }

    public EntityAttributeInputParameter withDefaultValue(Object defaultValue) {
        inputParameter
                .withDefaultValue(defaultValue);
        return this;
    }


    public EntityAttributeInputParameter withRequired(boolean required) {
        inputParameter
                .withRequired(required);
        return this;
    }

    public EntityAttributeInputParameter withCaption(String caption) {
        inputParameter
                .withCaption(caption);
        return this;
    }

    public EntityAttributeInputParameter withAutoBinding(boolean autoBinding) {
        this.autoBinding = autoBinding;
        return this;
    }

    public InputParameter getInputParameter() {
        return inputParameter;
    }


    public boolean isAutoBinding() {
        return autoBinding;
    }

    public MetaProperty getMetaProperty() {
        return metaProperty;
    }

    private static Metadata getMetadata() {
        return AppBeans.get(Metadata.class);
    }

    private static MessageTools getMessageTools() {
        return AppBeans.get(Messages.class).getTools();
    }

}
