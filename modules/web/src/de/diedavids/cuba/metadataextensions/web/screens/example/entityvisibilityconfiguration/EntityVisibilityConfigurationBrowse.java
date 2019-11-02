package de.diedavids.cuba.metadataextensions.web.screens.example.entityvisibilityconfiguration;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.metadataextensions.example.entity.EntityVisibilityConfiguration;

@UiController("ddcme_EntityVisibilityConfiguration.browse")
@UiDescriptor("entity-visibility-configuration-browse.xml")
@LookupComponent("entityVisibilityConfigurationsTable")
@LoadDataBeforeShow
public class EntityVisibilityConfigurationBrowse extends StandardLookup<EntityVisibilityConfiguration> {
}