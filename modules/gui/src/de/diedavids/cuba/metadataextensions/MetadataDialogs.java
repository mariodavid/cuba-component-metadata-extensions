package de.diedavids.cuba.metadataextensions;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.cuba.gui.screen.Screen;


public interface MetadataDialogs {


    String NAME = "ddcme_MetadataDialogs";

    MetadataInputDialogBuilder createMetadataInputDialog(FrameOwner owner);


    interface MetadataInputDialogBuilder {


        MetadataInputDialogBuilder withMetaProperty(MetaProperty property);

        MetadataInputDialogBuilder withEntityInstance(Entity entityInstance);

        InputDialog build();

        Screen show();


    }
}
