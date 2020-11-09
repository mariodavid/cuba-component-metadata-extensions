package de.diedavids.cuba.metadataextensions.entity.example;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|name")
@Table(name = "CEUME_PRODUCT")
@Entity(name = "ceume_Product")
public class Product extends StandardEntity {
    private static final long serialVersionUID = 5926257559521995013L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "TYPE_")
    protected String type;

    public ProductType getType() {
        return type == null ? null : ProductType.fromId(type);
    }

    public void setType(ProductType type) {
        this.type = type == null ? null : type.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}