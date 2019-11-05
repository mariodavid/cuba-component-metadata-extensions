package de.diedavids.cuba.metadataextensions.entity;

import com.haulmont.chile.core.model.MetaProperty;


/**
 * Interface to be implemented by entities reference an entity attribute as an attribute
 */
public interface EntityAttributeAware extends EntityAware {

    MetaProperty getEntityAttribute();

    void setEntityAttribute(MetaProperty entityAttribute);

}
