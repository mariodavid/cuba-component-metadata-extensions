package de.diedavids.cuba.metadataextensions.entity.example;

import com.haulmont.chile.core.annotations.NamePattern;
import de.diedavids.cuba.metadataextensions.entity.EntityAttributeAwareStandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|visible")
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

}