package de.diedavids.cuba.metadataextensions.datatype;

import com.google.common.base.Strings;
import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.Locale;

public class MetaClassDatatype implements Datatype<MetaClass> {

    @Override
    public Class getJavaClass() {
        return MetaClass.class;
    }

    @Nonnull
    @Override
    public String format(@Nullable Object value) {

        if (value == null) {
            return "";
        }

        MetaClass metaClass = (MetaClass) value;

        return metaClass.getName();

    }

    @Nonnull
    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    @Override
    public MetaClass parse(@Nullable String value) throws ParseException {

        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        Metadata metadata = getMetadata();

        return metadata.getClass(value);
    }

    @Nullable
    @Override
    public MetaClass parse(@Nullable String value, Locale locale) throws ParseException {
        return parse(value);
    }

    private Metadata getMetadata() {
        return AppBeans.get(Metadata.NAME);
    }

}
