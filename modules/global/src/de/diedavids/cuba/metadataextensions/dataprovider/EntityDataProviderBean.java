package de.diedavids.cuba.metadataextensions.dataprovider;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.entity.EntityOp;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Component(EntityDataProvider.NAME)
public class EntityDataProviderBean implements EntityDataProvider {

    @Inject
    private Metadata metadata;
    @Inject
    private Messages messages;
    @Inject
    private Security security;

    @Override
    public Map<String, MetaClass> entitiesLookupFieldOptions() {
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
    public List<MetaProperty> businessEntityAttributes(MetaClass entityMetaClass) {
        return entityMetaClass.getProperties()
                .stream()
                .filter(metaProperty ->
                        !getMetadataTools().isSystem(metaProperty)
                                && !getMetadataTools().isSystemLevel(metaProperty)
                )
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, MetaProperty> entityAttributesLookupFieldOptions(MetaClass metaClass) {
        return getLookupMetaProperties(metaClass.getProperties());
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
