package de.diedavids.cuba.metadataextensions.example.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.StandardEntity;
import de.diedavids.cuba.metadataextensions.converter.MetaClassConverter;
import de.diedavids.cuba.metadataextensions.converter.MetaPropertyConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "DDCME_ENTITY_VISIBILITY_CONFIGURATION")
@Entity(name = "ddcme_EntityVisibilityConfiguration")
public class EntityVisibilityConfiguration extends StandardEntity {
    private static final long serialVersionUID = -7814812092813294086L;


    @Convert(converter = MetaClassConverter.class)
    @MetaProperty(datatype = "MetaClass")
    @Column(name = "ENTITY")
    protected MetaClass entity;

    @Convert(converter = MetaPropertyConverter.class)
    @MetaProperty(datatype = "MetaProperty")
    @Column(name = "ENTITY_ATTRIBUTE")
    protected com.haulmont.chile.core.model.MetaProperty entityAttribute;

    @Column(name = "VISIBLE")
    protected Boolean visible;

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public com.haulmont.chile.core.model.MetaProperty getEntityAttribute() {
        return entityAttribute;
    }

    public void setEntityAttribute(com.haulmont.chile.core.model.MetaProperty entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    public MetaClass getEntity() {
        return entity;
    }

    public void setEntity(MetaClass entity) {
        this.entity = entity;
    }
}