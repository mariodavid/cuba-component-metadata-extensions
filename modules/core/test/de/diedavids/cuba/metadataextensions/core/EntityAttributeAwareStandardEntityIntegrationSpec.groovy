package de.diedavids.cuba.metadataextensions.core

import com.haulmont.chile.core.model.MetaClass
import com.haulmont.chile.core.model.MetaProperty
import com.haulmont.cuba.core.Persistence
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.security.entity.User
import de.diedavids.cuba.metadataextensions.DdcmeTestContainer
import de.diedavids.cuba.metadataextensions.entity.example.EntityVisibilityConfiguration
import de.diedavids.cuba.metadataextensions.entity.dummy.DummyEntityAttributeAwareStandardEntity
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class EntityAttributeAwareStandardEntityIntegrationSpec extends Specification {


    @Shared
    @ClassRule
    public DdcmeTestContainer cont = DdcmeTestContainer.Common.INSTANCE

    private Metadata metadata
    private Persistence persistence
    private DataManager dataManager

    void setup() {
        metadata = cont.metadata()
        persistence = cont.persistence()
        dataManager = AppBeans.get(DataManager)
    }

    def "an entity with a reference to an entity and entity attribute can be stored"() {

        given:
        EntityVisibilityConfiguration entityVisibilityConfiguration = metadata.create(EntityVisibilityConfiguration)


        def userMetaClass = metadata.getClass(User)
        entityVisibilityConfiguration.setEntity(userMetaClass)
        entityVisibilityConfiguration.setEntityAttribute(userMetaClass.getProperty('login'))
        entityVisibilityConfiguration.setVisible(true)

        when:
        dataManager.commit(entityVisibilityConfiguration)

        then:
        noExceptionThrown()
    }

    def "the entity and entity attribute cen be retrieved from the database"() {

        given:
        EntityVisibilityConfiguration testEntity = metadata.create(EntityVisibilityConfiguration)


        MetaClass userMetaClass = metadata.getClass(User)
        MetaProperty loginMetaProperty = userMetaClass.getProperty('login')

        and:
        testEntity.entity = userMetaClass
        testEntity.entityAttribute = loginMetaProperty
        testEntity.visible = true

        and:
        dataManager.commit(testEntity)

        when:
        EntityVisibilityConfiguration reloadedEntity = dataManager.load(EntityVisibilityConfiguration.class)
                .id(testEntity.getId())
                .one()

        then:
        reloadedEntity.visible
        reloadedEntity.entityAttribute == loginMetaProperty
        reloadedEntity.entity == userMetaClass
    }
}