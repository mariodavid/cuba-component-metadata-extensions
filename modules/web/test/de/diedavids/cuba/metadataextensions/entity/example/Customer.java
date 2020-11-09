package de.diedavids.cuba.metadataextensions.entity.example;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@NamePattern("%s|name")
@Table(name = "CEUME_CUSTOMER")
@Entity(name = "ceume_Customer")
public class Customer extends StandardEntity {
    private static final long serialVersionUID = 4868517366204938402L;

    @Column(name = "FIRST_NAME")
    protected String firstName;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "BIRTHDAY")
    protected LocalDate birthday;

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}