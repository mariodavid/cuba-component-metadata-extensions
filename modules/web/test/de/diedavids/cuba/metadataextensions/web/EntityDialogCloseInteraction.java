package de.diedavids.cuba.metadataextensions.web;

import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.components.inputdialog.InputDialogAction;
import com.haulmont.cuba.gui.util.OperationResult;
import de.diedavids.cuba.metadataextensions.EntityDialogFacet;
import de.diedavids.sneferu.InteractionWithOutcome;
import de.diedavids.sneferu.screen.InputDialogTestAPI;

public class EntityDialogCloseInteraction implements InteractionWithOutcome<OperationResult, InputDialogTestAPI> {

    @Override
    public OperationResult execute(InputDialogTestAPI screenTestAPI) {
        return screenTestAPI.screen().close(InputDialog.INPUT_DIALOG_OK_ACTION);
    }

}