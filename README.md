[![Build Status](https://travis-ci.com/mariodavid/cuba-component-metadata-extensions.svg?branch=master)](https://travis-ci.com/mariodavid/cuba-component-metadata-extensions)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-metadata-extensions/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-metadata-extensions/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# CUBA Component - Metadata Extensions

This application component that provides extensions for different use-cases for metadata interactions in CUBA.


Table of Contents
=================

  * [Installation](#installation)
  * [Using the application component](#using-the-application-component)


## Installation

1. `metadata-extensions` is available in the [CUBA marketplace](https://www.cuba-platform.com/marketplace/metadata-extensions)
2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 7.1.x            | 0.1.x          |


The latest version is: [ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-metadata-extensions/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-metadata-extensions/_latestVersion)

Add custom application component to your project:

* Artifact group: `de.diedavids.cuba.metadataextensions`
* Artifact name: `metadataextensions-global`
* Version: *add-on version*

```groovy
dependencies {
  appComponent("de.diedavids.cuba.metadataextensions:metadataextensions-global:*addon-version*")
}
```


### CHANGELOG

Information on changes that happen through the different versions of the application component can be found in the [CHANGELOG](https://github.com/mariodavid/cuba-component-metadata-extensions/blob/master/CHANGELOG.md).
The Changelog also contains information about breaking changes and tips on how to resolve them.



## Using the application component



### MetadataDialogs API

The `MetadataDialogs` API is an use-case extension of the `Dialogs` API provided by CUBA since version 7.0.

The MetadataDialogs provides the ability to create an `InputDialog` that was introduces in CUBA 7.1. Instead
of using this in the general way, where the native datatypes are references, the method `createMetadataInputDialog`
allows to specific one or multiple meta properties of an Entity. Based on the type that is defined in the Entity,
the `InputDialog` will contain the correct input fields.

The API mirrors the style of the existing `Dialogs` API. An example usage of this method looks like this:

```
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import de.diedavids.cuba.metadataextensions.MetadataDialogs;

import static de.diedavids.cuba.metadataextensions.MetaPropertyInputParameter.metaPropertyParameter;

public class CustomerBrowse extends StandardLookup<Customer> {

    @Inject
    protected MetadataDialogs metadataDialogs;

    @Subscribe("customersTable.quickChange")
    protected void onCustomersTableQuickChange(Action.ActionPerformedEvent event) {

        Customer customer = customersTable.getSingleSelected();

        metadataDialogs.createMetadataInputDialog(this, Customer.class)
                .withEntity(customer)
                .withCloseListener(closeEvent -> /* ... */ )
                .withCaption(messageBundle.getMessage("quickChange"))
                .withParameters(
                        metaPropertyParameter(Customer.class, "name")
                                .withAutoBinding(false),
                        metaPropertyParameter(Customer.class, "birthday")
                                .withAutoBinding(true)
                )
                .show();
    }

}
```


The static method `metaPropertyParameter` is leveraged here to easily create `MetaPropertyInputParameter` instances.

The `MetaPropertyInputParameter` is very similar to the `InputParameter` from CUBA.

#### Auto binding
There is one attribute called `autoBinding`, which can be set through the corresponding builder method: `withAutoBinding`.
It means, that in case the Metadata Input Dialog contains an entity reference through `withEntity(customer)`,
the value defined in the customer instance is used as the default value for the input field.
Furthermore it writes back the value that was entered during the Dialog into the field of the entity instance

For more information about the options for the parameter see [MetaPropertyInputParameter.java](https://github.com/mariodavid/cuba-component-metadata-extensions/blob/master/modules/gui/src/de/diedavids/cuba/metadataextensions/MetaPropertyInputParameter.java).
