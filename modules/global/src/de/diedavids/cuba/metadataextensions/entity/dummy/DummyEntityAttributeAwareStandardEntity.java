package de.diedavids.cuba.metadataextensions.entity.dummy;

import de.diedavids.cuba.metadataextensions.entity.EntityAttributeAwareStandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "DDCME_DUMMY_EAASE")
@Entity(name = "ddcme_DummyEntityAttributeAwareStandardEntity")
public class DummyEntityAttributeAwareStandardEntity extends EntityAttributeAwareStandardEntity {
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