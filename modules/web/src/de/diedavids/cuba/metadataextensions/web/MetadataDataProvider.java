package de.diedavids.cuba.metadataextensions.web;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;

import java.util.List;
import java.util.Map;

public interface MetadataDataProvider {

    String NAME = "ddcmu_MetadataDataProvider";


    Map<String, MetaProperty> getAllAttributesLookupFieldOptions(MetaClass entityMetaClass);

    Map<String, MetaClass> getEntitiesLookupFieldOptions();

    List<MetaProperty> getBusinessMetaProperties(MetaClass metaClass);
}
