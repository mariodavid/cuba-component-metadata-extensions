package de.diedavids.cuba.metadataextensions

import com.haulmont.chile.core.datatypes.impl.StringDatatype
import com.haulmont.chile.core.model.MetaClass
import com.haulmont.chile.core.model.MetaProperty
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.security.entity.Group
import com.haulmont.cuba.security.entity.Role
import com.haulmont.cuba.security.entity.RoleType
import com.haulmont.cuba.security.entity.User
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import static EntityAttributeInputParameter.*

class EntityAttributeInputParameterSpec extends Specification {


    @Shared
    @ClassRule
    public MetadataExtensionsWebTestContainer cont = MetadataExtensionsWebTestContainer.Common.INSTANCE

    private Metadata metadata;

    void setup() {
        metadata = AppBeans.get(Metadata.class);
    }


    def "metaPropertyParameter can set the default value"() {

        given:
        MetaProperty userLogin = userMetaClass().getProperty("login")

        when:
        EntityAttributeInputParameter parameter = entityAttributeParameter(userLogin)
                    .withDefaultValue("myUser")

        then:
        parameter.inputParameter.defaultValue == "myUser"
    }

    def "metaPropertyParameter sets the correct datatype of the input parameter for a String datatype"() {

        given:
        MetaProperty userLogin = userMetaClass().getProperty("login")

        when:
        EntityAttributeInputParameter parameter = entityAttributeParameter(userLogin)

        then:
        parameter.inputParameter.datatype instanceof StringDatatype
    }

    def "metaPropertyParameter sets the correct entity class for a entity reference"() {

        given:
        MetaProperty userGroup = userMetaClass().getProperty("group")

        when:
        EntityAttributeInputParameter parameter = entityAttributeParameter(userGroup)

        then:
        parameter.inputParameter.entityClass == Group.class
    }

    def "metaPropertyParameter sets the correct enum class for an enum property"() {

        given:
        MetaProperty roleType = metadata.getClass(Role.class).getProperty("type")

        when:
        EntityAttributeInputParameter parameter = entityAttributeParameter(roleType)

        then:
        parameter.inputParameter.enumClass == RoleType.class
    }

    private MetaClass userMetaClass() {
        metadata.getClass(User.class)
    }

}
