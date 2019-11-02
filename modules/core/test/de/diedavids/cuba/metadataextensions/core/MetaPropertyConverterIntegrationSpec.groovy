package de.diedavids.cuba.metadataextensions.core

import com.haulmont.chile.core.model.MetaClass
import com.haulmont.chile.core.model.MetaProperty
import com.haulmont.cuba.core.Persistence
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.security.entity.User
import de.diedavids.cuba.metadataextensions.DdcmeTestContainer
import de.diedavids.cuba.metadataextensions.converter.MetaPropertyConverter
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class MetaPropertyConverterIntegrationSpec extends Specification {


    @Shared
    @ClassRule
    public DdcmeTestContainer cont = DdcmeTestContainer.Common.INSTANCE


    Metadata metadata
    Persistence persistence
    DataManager dataManager
    MetaPropertyConverter converter

    void setup() {
        metadata = cont.metadata()
        persistence = cont.persistence()
        dataManager = AppBeans.get(DataManager)

        converter = new MetaPropertyConverter()
    }


    void "the metaProperty is represented as metaClass name + metaProperty name"() {

        when:
        def columnValue = converter.convertToDatabaseColumn(userMetaClass().getProperty('login'))

        then:
        columnValue == 'sec$User.login'

    }


    void "the metaProperty name is parsed from DB to identify the MetaClass"() {

        when:
        MetaProperty loginMetaProperty = converter.convertToEntityAttribute('sec$User.login')

        then:
        loginMetaProperty == userMetaClass().getProperty('login')

    }

    void "the metaProperty is null in case the DB value is null"() {

        expect:
        !converter.convertToEntityAttribute('')
        !converter.convertToEntityAttribute(null)

    }


    void "the metaProperty is null in case a non existing property is given"() {

        expect:
        !converter.convertToEntityAttribute('sec$User.notAvailableProperty')

    }
    void "the metaProperty is null in case a non an invalid String is provided"() {

        expect:
        !converter.convertToEntityAttribute('wrongString')

    }

    void "the DB value is null when no MetaClass provided"() {

        expect:
        !converter.convertToDatabaseColumn(null)

    }


    private MetaClass userMetaClass() {
        metadata.getClass(User)
    }
}
