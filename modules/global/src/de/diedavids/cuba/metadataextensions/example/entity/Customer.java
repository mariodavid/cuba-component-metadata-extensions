package de.diedavids.cuba.metadataextensions.example.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamePattern("%s|name")
@Table(name = "DDCME_CUSTOMER")
@Entity(name = "ddcme_Customer")
public class Customer extends StandardEntity {
    private static final long serialVersionUID = 3002502216573951182L;

    @Column(name = "NAME")
    protected String name;

    @NotNull
    @Column(name = "BIRTHDAY", nullable = false)
    protected LocalDate birthday;

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}