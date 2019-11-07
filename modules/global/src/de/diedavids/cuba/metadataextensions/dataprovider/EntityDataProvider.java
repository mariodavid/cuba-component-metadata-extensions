package de.diedavids.cuba.metadataextensions.dataprovider;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;

import java.util.List;
import java.util.Map;

public interface EntityDataProvider {

    String NAME = "ddcme_EntityDataProvider";


    /**
     * returns a Map of all {@link MetaClass} instances for the usage within a LookupField
     *
     * One entry maps the translated caption of the MetaClass to its instance.
     *
     * Example:
     * "User (sec$User)" --> User MetaClass
     * @return a Map of all MetaClasses
     */
    Map<String, MetaClass> entitiesLookupFieldOptions();


    /**
     * returns a Map of all {@link MetaProperty} instances for a given {@link MetaClass}.
     *
     * One entry maps the translated caption of the MetaProperty to its instance.
     *
     * Example:
     * "Login" --> User.login
     *
     * @param metaClass the metaClass to get all meta properties for
     *
     * @return a Map of all MetaProperties
     */
    Map<String, MetaProperty> entityAttributesLookupFieldOptions(MetaClass metaClass);


    /**
     *
     * returns a list of MetaProperties that are not marked as {@link com.haulmont.cuba.core.entity.annotation.SystemLevel}
     * or are part of system level interfaces like {@link com.haulmont.cuba.core.entity.HasUuid}.
     *
     * @param metaClass the metaClass to get all business meta properties for
     * @return a List of all business MetaProperties
     */
    List<MetaProperty> businessEntityAttributes(MetaClass metaClass);
}
