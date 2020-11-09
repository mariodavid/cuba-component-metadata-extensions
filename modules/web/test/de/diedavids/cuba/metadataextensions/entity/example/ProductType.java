package de.diedavids.cuba.metadataextensions.entity.example;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ProductType implements EnumClass<String> {

    LAPTOP("LAPTOP"),
    MOBILEPHONE("MOBILEPHONE");

    private String id;

    ProductType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ProductType fromId(String id) {
        for (ProductType at : ProductType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}