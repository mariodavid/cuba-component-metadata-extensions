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

import static de.diedavids.cuba.metadataextensions.MetaPropertyInputParameter.metaPropertyParameter

class MetadataDialogsSpec extends Specification {


    @Rule
    public TestUiEnvironment environment =
            new TestUiEnvironment(MetadataExtensionsWebTestContainer.Common.INSTANCE)
                    .withScreenPackages(
                            "com.haulmont.cuba.web.app.main",
                            "com.haulmont.cuba.gui.app.core.inputdialog"
                    )
                    .withUserLogin("admin")

    private Metadata metadata
    private MetadataDialogs metadataDialogs

    void setup() {
        metadata = AppBeans.get(Metadata.class);
        metadataDialogs = AppBeans.get(MetadataDialogs.class);
    }


    def "caption for the input dialog"() {

        when:
        InputDialog inputDialog = userMetadataInputDialog()
                .withCaption("caption of the dialog")
                .show()

        then:
        inputDialog.getDialogWindow().getCaption() == "caption of the dialog"
    }


    def "close listener is called when the screen is closed"() {

        given:
        boolean closeListenerCalled = false

        and:
        InputDialog inputDialog = userMetadataInputDialog()
                .withCaption("caption of the dialog")
                .withCloseListener(new Consumer<InputDialog.InputDialogCloseEvent>() {

                    @Override
                    void accept(InputDialog.InputDialogCloseEvent closeEvent) {
                        closeListenerCalled = true
                    }
                })
                .show()

        when:
        inputDialog.close(InputDialog.INPUT_DIALOG_OK_ACTION)

        then:
        closeListenerCalled
    }

    private Screen mainWindow() {
        environment.getScreens()
                .create(MainScreen.class, OpenMode.ROOT)
                .show();
    }

    private MetadataDialogs.MetadataInputDialogBuilder userMetadataInputDialog() {
        metadataDialogs.createMetadataInputDialog(mainWindow(), User.class)
    }
}
