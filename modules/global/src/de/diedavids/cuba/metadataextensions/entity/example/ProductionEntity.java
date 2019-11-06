package de.diedavids.cuba.metadataextensions.entity.example;

import de.diedavids.cuba.metadataextensions.entity.EntityAttributeAwareStandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "DDCME_PRODUCTION_ENTITY")
@Entity(name = "ddcme_ProductionEntity")
public class ProductionEntity extends EntityAttributeAwareStandardEntity {
    private static final long serialVersionUID = 4796224083824230331L;

    @Column(name = "TEST")
    protected String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}