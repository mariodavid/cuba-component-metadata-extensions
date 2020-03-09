package de.diedavids.cuba.metadataextensions;

import com.haulmont.bali.events.Subscription;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.meta.*;
import com.haulmont.cuba.gui.screen.CloseAction;

import java.util.EventObject;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Prepares and shows entity dialogs.
 */
@StudioFacet(
        caption = "Entity Dialog",
        description = "Prepares and shows entity dialogs",
        defaultProperty = "message",
        defaultEvent = "CloseEvent",
        xmlnsAlias = "ddcme",
        xmlElement = "entityDialog",
        xmlns = "http://schemas.diedavids.de/metadataextensions/0.1/ui-component.xsd"
)
@StudioProperties(
        properties = {
                @StudioProperty(name = "id", required = true),
                @StudioProperty(name = "caption"),
                @StudioProperty(name = "width"),
                @StudioProperty(name = "height")
        }
)
public interface EntityDialogFacet extends Facet, ActionsAwareDialogFacet<EntityDialogFacet>, HasSubParts {

    /**
     * Sets dialog caption.
     * @param caption caption
     */
    @StudioProperty(type = PropertyType.LOCALIZED_STRING)
    void setCaption(String caption);

    /**
     * @return dialog caption
     */
    String getCaption();

    /**
     * Sets dialog width.
     * @param width width
     */
    @StudioProperty
    void setWidth(String width);

    /**
     * @return dialog width
     */
    float getWidth();

    /**
     * @return dialog width size unit
     */
    SizeUnit getWidthSizeUnit();

    /**
     * Sets dialog height.
     * @param height height
     */
    @StudioProperty
    void setHeight(String height);

    /**
     * @return dialog height
     */
    float getHeight();

    /**
     * @return dialog height size unit
     */
    SizeUnit getHeightSizeUnit();

    /**
     * Sets that dialog should be shown when action with id {@code actionId}
     * is performed.
     *
     * @param actionId action id
     */
    @StudioProperty(type = PropertyType.COMPONENT_ID)
    void setActionTarget(String actionId);

    /**
     * @return id of action that triggers dialog
     */
    String getActionTarget();

    /**
     * Sets that dialog should be shown when button with id {@code actionId}
     * is clicked.
     *
     * @param buttonId button id
     */
    @StudioProperty(type = PropertyType.COMPONENT_ID)
    void setButtonTarget(String buttonId);

    /**
     * @return id of button that triggers dialog
     */
    String getButtonTarget();



    /**
     * Adds the given {@code Consumer} as dialog {@link InputDialog.InputDialogCloseEvent} listener.
     *
     * @param closeListener close listener
     *
     * @return close event subscription
     */
    @StudioEvent
    Subscription addCloseListener(Consumer<CloseEvent> closeListener);

    /**
     * Sets input dialog result handler.
     *
     * @param dialogResultHandler result handler
     */
    @StudioDelegate
    void setDialogResultHandler(Consumer<InputDialog.InputDialogResult> dialogResultHandler);

    /**
     * Sets additional handler for field validation. It receives input dialog context and must return {@link ValidationErrors}
     * instance. Returned validation errors will be shown with another errors from fields.
     *
     * @param validator validator
     */
    @StudioDelegate
    void setValidator(Function<InputDialog.ValidationContext, ValidationErrors> validator);

    /**
     * Sets input dialog parameters.
     *
     * @param parameters set of {@link InputParameter}
     */
    void setParameters(EntityAttributeInputParameter... parameters);

    /**
     * Creates InputDialog.
     *
     * @return input dialog instance
     */
    InputDialog create();

    /**
     * Shows InputDialog.
     */
    InputDialog show();

    void setEntityClass(MetaClass entityClass);

    MetaClass getEntityClass();

    /**
     * Event that is fired when EntityDialog is closed.
     */
    class CloseEvent extends EventObject {

        protected CloseAction closeAction;
        protected Map<String, Object> values;

        public CloseEvent(EntityDialogFacet source, CloseAction closeAction, Map<String, Object> values) {
            super(source);
            this.closeAction = closeAction;
            this.values = values;
        }

        @Override
        public EntityDialogFacet getSource() {
            return (EntityDialogFacet) super.getSource();
        }

        public CloseAction getCloseAction() {
            return closeAction;
        }

        public Map<String, Object> getValues() {
            return values;
        }
    }
}
