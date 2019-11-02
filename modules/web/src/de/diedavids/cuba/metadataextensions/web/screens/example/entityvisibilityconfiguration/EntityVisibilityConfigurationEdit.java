package de.diedavids.cuba.metadataextensions.web.screens.example.entityvisibilityconfiguration;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.metadataextensions.example.entity.EntityVisibilityConfiguration;
import de.diedavids.cuba.metadataextensions.web.MetadataDataProvider;

import javax.inject.Inject;

@UiController("ddcme_EntityVisibilityConfiguration.edit")
@UiDescriptor("entity-visibility-configuration-edit.xml")
@EditedEntityContainer("entityVisibilityConfigurationDc")
@LoadDataBeforeShow
public class EntityVisibilityConfigurationEdit extends StandardEditor<EntityVisibilityConfiguration> {

    @Inject
    protected LookupField<MetaClass> entityField;

    @Inject
    protected MetadataDataProvider metadataDataProvider;
    @Inject
    protected LookupField<MetaProperty> entityAttributeField;

    @Subscribe
    protected void onInit(InitEvent event) {
        entityField.setOptionsMap(metadataDataProvider.getEntitiesLookupFieldOptions());
    }

    @Subscribe("entityField")
    protected void onEntityFieldValueChange(HasValue.ValueChangeEvent<MetaClass> event) {

        if (event.getValue() != null) {
            entityAttributeField.setOptionsMap(
                    metadataDataProvider.getAllAttributesLookupFieldOptions(event.getValue())
            );
        }
    }
    
    
    
    
}