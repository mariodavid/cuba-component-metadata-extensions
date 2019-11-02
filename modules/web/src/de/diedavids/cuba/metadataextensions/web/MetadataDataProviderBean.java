package de.diedavids.cuba.metadataextensions.web;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.entity.EntityOp;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Component(MetadataDataProvider.NAME)
public class MetadataDataProviderBean implements MetadataDataProvider {

    @Inject
    private Metadata metadata;
    @Inject
    private Messages messages;
    @Inject
    private Security security;

    @Override
    public Map<String, MetaClass> getEntitiesLookupFieldOptions() {
        TreeMap<String, MetaClass> options = new TreeMap<>();

        for (MetaClass metaClass : getMetadataTools().getAllPersistentMetaClasses()) {
            if (readPermitted(metaClass)) {
                Class javaClass = metaClass.getJavaClass();
                if (Entity.class.isAssignableFrom(javaClass)) {
                    options.put(getMessageTools().getEntityCaption(metaClass) + " (" + metaClass.getName() + ")", metaClass);
                }

            }

        }


        return options;
    }

    @Override
    public List<MetaProperty> getBusinessMetaProperties(MetaClass entityMetaClass) {
        return Collections.emptyList();
    }

    @Override
    public Map<String, MetaProperty> getAllAttributesLookupFieldOptions(MetaClass entityMetaClass) {
        return getLookupMetaProperties(entityMetaClass.getProperties());
    }

    public Map<String, MetaProperty> getLookupMetaProperties(Collection<MetaProperty> metaProperties) {
        return metaProperties.stream()
                .collect(Collectors.toMap(
                        metaProperty -> getMessageTools().getPropertyCaption(metaProperty),
                        metaProperty -> metaProperty
                ));
    }

    protected boolean readPermitted(MetaClass metaClass) {
        return entityOpPermitted(metaClass, EntityOp.READ);
    }

    protected boolean entityOpPermitted(MetaClass metaClass, EntityOp entityOp) {
        return security.isEntityOpPermitted(metaClass, entityOp);
    }

    private MessageTools getMessageTools() {
        return messages.getTools();
    }

    private MetadataTools getMetadataTools() {
        return metadata.getTools();
    }

}
