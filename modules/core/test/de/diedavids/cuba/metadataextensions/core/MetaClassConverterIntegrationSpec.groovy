package de.diedavids.cuba.metadataextensions.core

import com.haulmont.cuba.core.Persistence
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.security.entity.User
import de.diedavids.cuba.metadataextensions.DdcmeTestContainer
import de.diedavids.cuba.metadataextensions.converter.MetaClassConverter
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class MetaClassConverterIntegrationSpec extends Specification {


    @Shared
    @ClassRule
    public DdcmeTestContainer cont = DdcmeTestContainer.Common.INSTANCE


    Metadata metadata
    Persistence persistence
    DataManager dataManager
    MetaClassConverter converter

    void setup() {
        metadata = cont.metadata()
        persistence = cont.persistence()
        dataManager = AppBeans.get(DataManager)

        converter = new MetaClassConverter()
    }


    void "the metaClass name is used to represent the MetaClass in the DB"() {

        given:
        User user = metadata.create(User)

        when:
        def columnValue = converter.convertToDatabaseColumn(user.getMetaClass())

        then:
        columnValue == 'sec$User'

    }

    void "the metaClass name is parsed from DB to identify the MetaClass"() {

        when:
        def columnValue = converter.convertToEntityAttribute('sec$User')

        then:
        columnValue == metadata.getClass('sec$User')

    }

    void "the metaClass is null in case the DB value is null"() {

        expect:
        !converter.convertToEntityAttribute('')
        !converter.convertToEntityAttribute(null)

    }



    void "the metaClass is null in case an invalid metaClass String is given"() {

        expect:
        !converter.convertToEntityAttribute('invalidMetaClass')

    }


    void "the DB value is null when no MetaClass provided"() {

        expect:
        !converter.convertToDatabaseColumn(null)

    }


}
