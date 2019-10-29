package de.diedavids.cuba.metadataextensions;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.screen.FrameOwner;



public interface MetadataDialogs extends Dialogs {


    String NAME = "ddcme_MetadataDialogs";

    MetadataInputDialogBuilder createMetadataInputDialog(FrameOwner owner);


    interface MetadataInputDialogBuilder extends Dialogs.InputDialogBuilder {


        MetadataInputDialogBuilder withMetaProperty(MetaProperty property);

        MetadataInputDialogBuilder withEntityInstance(Entity entityInstance);


    }
}
