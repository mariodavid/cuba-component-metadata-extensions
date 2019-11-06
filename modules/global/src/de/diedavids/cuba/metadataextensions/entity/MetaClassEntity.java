package de.diedavids.cuba.metadataextensions.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.model.MetadataObject;
import com.haulmont.cuba.core.entity.BaseUuidEntity;


/**
 * non-persistent entity that represents a {@link com.haulmont.chile.core.model.MetaClass} instance
 */
@NamePattern("%s|name")
@MetaClass(name = "ddcme_MetaClassEntity")
public class MetaClassEntity extends BaseUuidEntity {
    private static final long serialVersionUID = 7143382196961171961L;


    /**
     * the name of the MetaClass. Contains attribute of {@link MetadataObject#getName()}
     */
    @MetaProperty
    protected String name;

    /**
     * the description of the MetaClass. Container for the translation.
     * See {@link com.haulmont.cuba.core.global.MessageTools#getPropertyCaption(com.haulmont.chile.core.model.MetaProperty)}
     */
    @MetaProperty
    protected String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}