package de.diedavids.cuba.metadataextensions.web;

import com.haulmont.bali.util.ReflectionHelper;
import com.haulmont.chile.core.datatypes.DatatypeRegistry;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.global.MessageTools;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.GuiDevelopmentException;
import com.haulmont.cuba.gui.icons.Icons;
import com.haulmont.cuba.gui.theme.ThemeConstants;
import com.haulmont.cuba.gui.theme.ThemeConstantsManager;
import com.haulmont.cuba.gui.xml.FacetProvider;
import com.haulmont.cuba.gui.xml.layout.ComponentLoader;
import de.diedavids.cuba.metadataextensions.EntityAttributeInputParameter;
import de.diedavids.cuba.metadataextensions.EntityDialogFacet;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.haulmont.cuba.gui.icons.Icons.ICON_NAME_REGEX;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Component(EntityDialogFacetProvider.NAME)
public class EntityDialogFacetProvider implements FacetProvider<EntityDialogFacet> {
    public static final String NAME = "ddcme_EntityDialogFacetProvider";

    @Inject
    protected MessageTools messageTools;
    @Inject
    protected Metadata metadata;
    @Inject
    protected Icons icons;
    @Inject
    protected ThemeConstantsManager themeConstantsManager;

    @Override
    public Class<EntityDialogFacet> getFacetClass() {
        return EntityDialogFacet.class;
    }

    @Override
    public EntityDialogFacet create() {
        return new WebEntityDialogFacet();
    }

    @Override
    public String getFacetTag() {
        return "entityDialog";
    }

    @Override
    public void loadFromXml(
            EntityDialogFacet facet,
            Element element,
            ComponentLoader.ComponentContext context
    ) {
        loadId(facet, element);
        loadCaption(facet, element, context);

        loadWidth(facet, element);
        loadEntityClass(facet, element, context);
        loadHeight(facet, element);

        loadTarget(facet, element, context);

        loadEntityAttributeParameters(facet, element, context);
    }

    private void loadEntityClass(EntityDialogFacet facet, Element element, ComponentLoader.ComponentContext context) {
        String classFqn = element.attributeValue("entityClass");

        Class clazz = loadParamClass(element, classFqn, context);
        MetaClass entityClass = metadata.getClass(clazz);

        if (entityClass != null) {
            facet.setEntityClass(entityClass);
        } else {
            throw new GuiDevelopmentException(
                    String.format(
                            "Unable to create EntityDialog '%s'. Class '%s' is not entity class",
                            facet.getId(), classFqn),
                    context);
        }
    }

    protected void loadId(EntityDialogFacet facet, Element element) {
        String id = element.attributeValue("id");
        if (isNotEmpty(id)) {
            facet.setId(id);
        }
    }


    protected void loadCaption(EntityDialogFacet facet,
                               Element element,
                               ComponentLoader.ComponentContext context
    ) {
        String caption = element.attributeValue("caption");
        if (isNotEmpty(caption)) {
            facet.setCaption(loadResourceString(context, caption));
        }
    }

    protected void loadWidth(EntityDialogFacet facet, Element element) {
        String width = element.attributeValue("width");
        if (isNotEmpty(width)) {
            facet.setWidth(width);
        }
    }

    protected void loadHeight(EntityDialogFacet facet, Element element) {
        String height = element.attributeValue("height");
        if (isNotEmpty(height)) {
            facet.setHeight(height);
        }
    }

    protected void loadTarget(EntityDialogFacet facet, Element element,
                              ComponentLoader.ComponentContext context) {
        String actionTarget = element.attributeValue("onAction");
        String buttonTarget = element.attributeValue("onButton");

        if (isNotEmpty(actionTarget)
                && isNotEmpty(buttonTarget)) {
            throw new GuiDevelopmentException(
                    "InputDialog facet should have either action or button target",
                    context);
        }

        if (isNotEmpty(actionTarget)) {
            facet.setActionTarget(actionTarget);
        } else if (isNotEmpty(buttonTarget)) {
            facet.setButtonTarget(buttonTarget);
        }
    }


    protected void loadEntityAttributeParameters(
            EntityDialogFacet facet,
            Element element,
            ComponentLoader.ComponentContext context
    ) {
        List<EntityAttributeInputParameter> inputParameters = new ArrayList<>();
        Set<String> paramIds = new HashSet<>();

        Element paramsEl = element.element("parameters");
        if (paramsEl == null) {
            return;
        }

        for (Element paramEl : paramsEl.elements()) {
            String paramId = paramEl.attributeValue("id");

            if (!paramIds.contains(paramId)) {
                inputParameters.add(loadInputParameter(paramEl, facet.getEntityClass(), context));
                paramIds.add(paramId);
            } else {
                throw new GuiDevelopmentException("InputDialog parameters should have unique ids", context);
            }
        }

        if (inputParameters.isEmpty()) {
            throw new GuiDevelopmentException("InputDialog Facet cannot be used without parameters", context);
        }

        facet.setParameters(inputParameters.toArray(new EntityAttributeInputParameter[]{}));
    }

    protected EntityAttributeInputParameter loadInputParameter(
            Element paramEl,
            MetaClass entityClass,
            ComponentLoader.ComponentContext context
    ) {
        String paramName = paramEl.getName();
        if ("entityAttributeParameter".equals(paramName)) {
            return loadEntityAttributeParameter(paramEl, entityClass, context);
        } else {
            throw new GuiDevelopmentException(
                    String.format("Unsupported type '%s' of EntityDialog parameter '%s'",
                            paramName, paramEl.attributeValue("id")),
                    context);
        }
    }

    @SuppressWarnings("unchecked")
    protected EntityAttributeInputParameter loadEntityAttributeParameter(
            Element paramEl,
            MetaClass entityClass,
            ComponentLoader.ComponentContext context
    ) {
        String property = paramEl.attributeValue("property");

        EntityAttributeInputParameter entityAttributeInputParameter = EntityAttributeInputParameter.entityAttributeParameter(entityClass.getPropertyNN(property));

        loadParamCaption(entityAttributeInputParameter, paramEl, context);
        loadParamAutoBinding(entityAttributeInputParameter, paramEl);
        loadParamDefaultValue(entityAttributeInputParameter, paramEl);

        return entityAttributeInputParameter;

    }

    protected void loadParamCaption(
            EntityAttributeInputParameter entityAttributeInputParameter,
            Element paramEl,
            ComponentLoader.ComponentContext context
    ) {
        String caption = paramEl.attributeValue("caption");
        if (isNotEmpty(caption)) {
            entityAttributeInputParameter.withCaption(
                    loadResourceString(context, caption)
            );
        }
    }
    protected void loadParamDefaultValue(
            EntityAttributeInputParameter entityAttributeInputParameter,
            Element paramEl
    ) {
        String defaultValue = paramEl.attributeValue("defaultValue");
        if (isNotEmpty(defaultValue)) {
            entityAttributeInputParameter.withDefaultValue(
                    defaultValue
            );
        }
    }

    protected void loadParamAutoBinding(
            EntityAttributeInputParameter entityAttributeInputParameter,
            Element paramEl
    ) {
        String required = paramEl.attributeValue("autoBinding");
        if (isNotEmpty(required)) {
            entityAttributeInputParameter
                    .withAutoBinding(Boolean.parseBoolean(required));
        }
    }

    protected Class loadParamClass(Element element, String classFqn,
                                   ComponentLoader.ComponentContext context) {
        try {
            return ReflectionHelper.loadClass(classFqn);
        } catch (ClassNotFoundException e) {
            throw new GuiDevelopmentException(
                    String.format(
                            "Unable to create InputDialog parameter '%s'. Class '%s' not found",
                            element.attributeValue("id"), classFqn),
                    context);
        }
    }

    protected String loadResourceString(ComponentLoader.ComponentContext context, String caption) {
        if (isEmpty(caption)) {
            return caption;
        }

        Class screenClass = context.getFrame()
                .getFrameOwner()
                .getClass();

        return messageTools.loadString(screenClass.getPackage().getName(), caption);
    }

    protected String getIconPath(ComponentLoader.ComponentContext context, String icon) {
        if (icon == null || icon.isEmpty()) {
            return null;
        }

        String iconPath = null;

        if (ICON_NAME_REGEX.matcher(icon).matches()) {
            iconPath = icons.get(icon);
        }

        if (isEmpty(iconPath)) {
            String themeValue = loadThemeString(icon);
            iconPath = loadResourceString(context, themeValue);
        }

        return iconPath;
    }

    protected String loadThemeString(String value) {
        if (value != null && value.startsWith(ThemeConstants.PREFIX)) {
            value = themeConstantsManager.getConstants()
                    .get(value.substring(ThemeConstants.PREFIX.length()));
        }
        return value;
    }
}
