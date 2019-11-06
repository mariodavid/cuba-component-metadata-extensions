package de.diedavids.cuba.metadataextensions.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.StandardEntity;
import de.diedavids.cuba.metadataextensions.converter.MetaPropertyConverter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;


/**
 * Base class for Entities that reference a specific entity (mostly per-entity configuration entities) <br>
 * Optimistically locked, implements Updatable and SoftDelete.
 */
@MappedSuperclass
public class EntityAwareStandardEntity extends StandardEntity implements EntityAware {
    private static final long serialVersionUID = -1405141025096148621L;

    @Convert(converter = MetaPropertyConverter.class)
    @MetaProperty(datatype = "MetaClass")
    @Column(name = "ENTITY_META_CLASS")
    protected MetaClass entityMetaClass;

    @Override
    public MetaClass getEntityMetaClass() {
        return entityMetaClass;
    }

    @Override
    public void setEntityMetaClass(MetaClass entityMetaClass) {
        this.entityMetaClass = entityMetaClass;
    }

}