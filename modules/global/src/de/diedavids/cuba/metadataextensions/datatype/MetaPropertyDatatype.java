package de.diedavids.cuba.metadataextensions.datatype;

import com.google.common.base.Strings;
import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.chile.core.model.impl.MetaPropertyImpl;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.Locale;

public class MetaPropertyDatatype implements Datatype<MetaProperty> {


    @Override
    public Class getJavaClass() {
        return MetaProperty.class;
    }

    @Nonnull
    @Override
    public String format(@Nullable Object value) {

        if (value == null) {
            return "";
        }

        MetaProperty metaProperty = (MetaProperty) value;

        return metaProperty.toString();

    }

    @Nonnull
    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    @Override
    public MetaProperty parse(@Nullable String value) throws ParseException {

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

    @Nullable
    @Override
    public MetaProperty parse(@Nullable String value, Locale locale) throws ParseException {
        return parse(value);
    }

    private Metadata getMetadata() {
        return AppBeans.get(Metadata.NAME);
    }

}
