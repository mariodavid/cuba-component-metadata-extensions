package de.diedavids.cuba.metadataextensions.entity;

import com.haulmont.chile.core.model.MetaClass;


/**
 * Interface to be implemented by entities reference an entity as an attribute
 */
public interface EntityAware {

    MetaClass getEntityMetaClass();

    void setEntityMetaClass(MetaClass entity);
}
