package de.diedavids.cuba.metadataextensions

import com.haulmont.chile.core.model.MetaClass
import com.haulmont.chile.core.model.MetaProperty
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog
import com.haulmont.cuba.gui.components.TextInputField
import com.haulmont.cuba.gui.screen.OpenMode
import com.haulmont.cuba.gui.screen.Screen
import com.haulmont.cuba.security.entity.Group
import com.haulmont.cuba.security.entity.User
import com.haulmont.cuba.web.app.main.MainScreen
import com.haulmont.cuba.web.testsupport.TestUiEnvironment
import org.junit.Rule
import spock.lang.Specification

import java.util.function.Consumer

import static EntityAttributeInputParameter.entityAttributeParameter

class EntityDialogsDatabindingSpec extends Specification {

    @Rule
    public TestUiEnvironment environment =
            new TestUiEnvironment(MetadataExtensionsWebTestContainer.Common.INSTANCE)
                    .withScreenPackages(
                            "com.haulmont.cuba.web.app.main",
                            "com.haulmont.cuba.gui.app.core.inputdialog"
                    )
                    .withUserLogin("admin")

    private Metadata metadata
    private EntityDialogs metadataDialogs

    void setup() {
        metadata = AppBeans.get(Metadata.class);
        metadataDialogs = AppBeans.get(EntityDialogs.class);
    }


    def "the default values of all parameters for the input dialog"() {

        given:
        def group = metadata.create(Group)

        when:
        InputDialog inputDialog = userMetadataInputDialog()
                .withParameters(
                        entityAttributeParameter(userLogin())
                                .withDefaultValue("myUser"),
                        entityAttributeParameter(userGroup())
                                .withDefaultValue(group),
                )
                .show()

        then:
        inputDialog.values == ["login": "myUser", "group": group]
    }

    def "the default value of the entity instance if the parameter enabled auto binding"() {

        given:
        User myUser = metadata.create(User)
        myUser.login = "myLogin"

        when:
        InputDialog inputDialog = userMetadataInputDialog()
                .withEntity(myUser)
                .withParameter(
                        entityAttributeParameter(userLogin())
                                .withAutoBinding(true)
                )
                .show()

        then:
        inputDialog.values == ["login": "myLogin"]
    }

    def "the provided value in the entity instance for a auto binding parameter"() {

        given:
        User myUser = metadata.create(User)
        myUser.login = "myLogin"

        and:
        InputDialog inputDialog = userMetadataInputDialog()
                .withEntity(myUser)
                .withParameter(
                        entityAttributeParameter(userLogin())
                                .withAutoBinding(true)
                )
                .show()

        and:
        TextInputField<String> loginField = (TextInputField<String>) inputDialog.getWindow().getComponent("login")

        when:
        loginField.setValue("providedLogin")

        and:
        inputDialog.close(InputDialog.INPUT_DIALOG_OK_ACTION)

        then:
        myUser.login == "providedLogin"
    }

    def "when no entity instance is provided, no data binding happens and the result map is receivable in the closeListener"() {

        given:
        Map<String, Object> receivedValues = [:]

        and:
        InputDialog inputDialog = userMetadataInputDialog()
                .withParameter(
                        entityAttributeParameter(userLogin())
                                .withAutoBinding(true)
                )
                .withCloseListener(new Consumer<InputDialog.InputDialogCloseEvent>() {

                    @Override
                    void accept(InputDialog.InputDialogCloseEvent closeEvent) {
                        receivedValues = closeEvent.values
                    }
                })
                .show()

        and:
        TextInputField<String> loginField = (TextInputField<String>) inputDialog.getWindow().getComponent("login")

        when:
        loginField.setValue("providedLogin")

        and:
        inputDialog.close(InputDialog.INPUT_DIALOG_OK_ACTION)

        then:
        receivedValues == ["login": "providedLogin"]
    }

    def "when an entity instance is provided data binding happens and the result map is receivable in the closeListener"() {

        given:
        User myUser = metadata.create(User)
        myUser.login = "myLogin"

        and:
        Group group = metadata.create(Group)
        myUser.group = group

        and:
        Map<String, Object> receivedValues = [:]

        and:
        InputDialog inputDialog = userMetadataInputDialog()
                .withEntity(myUser)
                .withParameters(
                        entityAttributeParameter(userLogin())
                                .withAutoBinding(true),
                        entityAttributeParameter(userGroup())
                                .withAutoBinding(true),
                )
                .withCloseListener(new Consumer<InputDialog.InputDialogCloseEvent>() {

                    @Override
                    void accept(InputDialog.InputDialogCloseEvent closeEvent) {
                        receivedValues = closeEvent.values
                    }
                })
                .show()

        and:
        TextInputField<String> loginField = (TextInputField<String>) inputDialog.getWindow().getComponent("login")

        when:
        loginField.setValue("providedLogin")

        and:
        inputDialog.close(InputDialog.INPUT_DIALOG_OK_ACTION)

        then:
        receivedValues == [login: "providedLogin", group: group]
    }

    def "no values for the entity instance in case cancel was performed"() {

        given:
        User myUser = metadata.create(User)
        myUser.login = "myLogin"

        and:
        InputDialog inputDialog = userMetadataInputDialog()
                .withEntity(myUser)
                .withParameter(
                        entityAttributeParameter(userLogin())
                                .withAutoBinding(true)
                )
                .show()

        and:
        TextInputField<String> loginField = (TextInputField<String>) inputDialog.getWindow().getComponent("login")

        when:
        loginField.setValue("providedLogin")

        and:
        inputDialog.close(InputDialog.INPUT_DIALOG_CANCEL_ACTION)

        then:
        myUser.login == "myLogin"
    }


    private Screen mainWindow() {
        environment.getScreens()
                .create(MainScreen.class, OpenMode.ROOT)
                .show();
    }

    private EntityDialogs.EntityInputDialogBuilder userMetadataInputDialog() {
        metadataDialogs.createEntityInputDialog(mainWindow(), User.class)
    }

    private MetaProperty userLogin() {
        userMetaClass().getProperty("login")
    }
    private MetaProperty userGroup() {
        userMetaClass().getProperty("group")
    }

    private MetaClass userMetaClass() {
        metadata.getClass(User.class)
    }

}
