package de.diedavids.cuba.metadataextensions.example.entity;

import com.haulmont.chile.core.model.MetaClass;
import de.diedavids.cuba.metadataextensions.entity.EntityAttributeAwareStandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "DDCME_ENTITY_VISIBILITY_CONFIGURATION")
@Entity(name = "ddcme_EntityVisibilityConfiguration")
public class EntityVisibilityConfiguration extends EntityAttributeAwareStandardEntity {
    private static final long serialVersionUID = -7814812092813294086L;


    @Column(name = "VISIBLE")
    protected Boolean visible;

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public com.haulmont.chile.core.model.MetaProperty getEntityAttribute() {
        return entityAttribute;
    }

    @Override
    public void setEntityAttribute(com.haulmont.chile.core.model.MetaProperty entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    @Override
    public MetaClass getEntity() {
        return entity;
    }

    @Override
    public void setEntity(MetaClass entity) {
        this.entity = entity;
    }
}