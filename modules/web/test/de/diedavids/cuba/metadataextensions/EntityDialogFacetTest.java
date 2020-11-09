package de.diedavids.cuba.metadataextensions;

import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.gui.util.OperationResult;
import com.haulmont.cuba.web.app.main.MainScreen;
import com.haulmont.cuba.web.testsupport.proxy.DataServiceProxy;
import com.haulmont.cuba.web.testsupport.proxy.TestServiceProxy;
import de.diedavids.cuba.metadataextensions.entity.example.Product;
import de.diedavids.cuba.metadataextensions.web.screens.product.ProductBrowse;
import de.diedavids.sneferu.environment.SneferuTestUiEnvironment;
import de.diedavids.sneferu.environment.StartScreen;
import de.diedavids.sneferu.screen.InputDialogTestAPI;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static de.diedavids.cuba.metadataextensions.MetadataExtensionsInteractions.closeEntityInputDialog;
import static de.diedavids.sneferu.ComponentDescriptors.button;
import static de.diedavids.sneferu.ComponentDescriptors.textField;
import static de.diedavids.sneferu.Interactions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class EntityDialogFacetTest {

    @RegisterExtension
    public SneferuTestUiEnvironment environment =
            new SneferuTestUiEnvironment(MetadataExtensionsWebTestContainer.Common.INSTANCE)
                    .withScreenPackages(
                            "com.haulmont.cuba.web.app.main",
                            "com.haulmont.cuba.gui.app.core.inputdialog",
                            "de.diedavids.cuba.metadataextensions"
                    )
                    .withUserLogin("admin")
                    .withMainScreen(MainScreen.class);


    protected DataService dataService;

    @BeforeEach
    void setUp() {
        dataService = Mockito.spy(
                new DataServiceProxy(environment.getContainer())
        );
        TestServiceProxy.mock(DataService.class, dataService);

    }

    @Test
    public void aVisitCanBeCreated_whenAllFieldsAreFilled(
            @StartScreen StandardLookupTestAPI<Product, ProductBrowse> productBrowse
    ) {


        // and:
        productBrowse.interact(click(button("createLaptopBtn")));

        InputDialogTestAPI createLaptopDialog = environment.getUiTestAPI().openedInputDialog();


        // when:
        OperationResult outcome = (OperationResult) createLaptopDialog
                .interact(enter(textField("name"), "Apple Macbook Pro 13"))
                .andThenGet(closeEntityInputDialog());

        // then:
        assertThat(outcome).isEqualTo(OperationResult.success());

        // and:
        assertThat(environment.getUiTestAPI().isActive(productBrowse))
                .isTrue();

        // and:
        ArgumentCaptor<CommitContext> commitContext = ArgumentCaptor.forClass(CommitContext.class);
        verify(dataService).commit(commitContext.capture());

        // and:
        assertThat(
                commitContext.getValue().getCommitInstances()
        )
                .hasSize(1);

    }

    @AfterEach
    public void closeAllScreens() {
        environment.getUiTestAPI().closeAllScreens();
        TestServiceProxy.clear();
    }
}
