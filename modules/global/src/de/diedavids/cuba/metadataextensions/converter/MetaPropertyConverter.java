package de.diedavids.cuba.metadataextensions.converter;

import com.google.common.base.Strings;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class MetaPropertyConverter implements AttributeConverter<MetaProperty, String> {

    @Override
    public String convertToDatabaseColumn(MetaProperty value) {

        if (value == null) {
            return "";
        }

        return value.toString();

    }

    @Override
    public MetaProperty convertToEntityAttribute(String value) {

        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        String[] nameParts = value.split("\\.");

        if (nameParts.length != 2) {
            return null;
        }

        String metaClassName = nameParts[0];
        String metaPropertyName = nameParts[1];

        return getMetadata().getClasses()
                .stream()
                .filter(metaClass -> metaClassName.equals(metaClass.getName()))
                .findFirst()
                .map(metaClass -> metaClass.getProperty(metaPropertyName))
                .orElse(null);

    }


    private Metadata getMetadata() {
        return AppBeans.get(Metadata.NAME);
    }

}
