package de.diedavids.cuba.metadataextensions.web.screens.product;

import de.diedavids.cuba.metadataextensions.entity.example.Product;
import com.haulmont.cuba.gui.screen.*;

@UiController("ceume_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
@LoadDataBeforeShow
public class ProductEdit extends StandardEditor<Product> {
}